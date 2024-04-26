package com.example.rentingapp.api.reservation

import com.example.rentingapp.api.apartment.ApartmentService
import com.example.rentingapp.api.apartment.dto.ApartmentSimpleDTO
import com.example.rentingapp.api.reservation.dto.ReservationDTO
import com.example.rentingapp.api.reservation.dto.ReservationSaveDto
import com.example.rentingapp.api.review.dto.ReviewDto
import com.example.rentingapp.api.review.dto.ReviewSaveDTO
import com.example.rentingapp.api.user.dto.UserDto
import com.example.rentingapp.data.domain.*
import com.example.rentingapp.data.repository.*
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.security.Principal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.Exception
import kotlin.collections.MutableList

@Service
class ReservationService(
    val reservationRepository: ReservationRepository,
    val apartmentService: ApartmentService,
    val apartmentRepository: ApartmentRepository,
    val userRepository: UserRepository,
    val stateRepository: StateRepository,
    val reviewRepository: ReviewRepository
) {

    fun getDetails(id: Long): ReservationDTO {
        val reservation = reservationRepository.findById(id)
            .orElseThrow { NoSuchElementException("There is no reservation with the id") }
        return ReservationDTO(
            reservation.id, reservation.startDate, reservation.endDate,
            UserDto(reservation.client.id, reservation.client.username, reservation.client.role),
            reservation.currentStatus,
            reservation.review?.text,
            ApartmentSimpleDTO(reservation.apartment.id, reservation.apartment.name, reservation.apartment.location),
            reservation.price
        )
    }

    fun showReservations(principal: Principal): List<ReservationDTO> {

        val reservations: List<Reservation>

        val loggedUser = userRepository.findByUsername(principal.name)

         reservations = when (loggedUser.role) {
            RoleEnum.CLIENT -> reservationRepository.findAllByClientId(loggedUser.id)
            RoleEnum.MANAGER -> reservationRepository.findAllByApartmentManagerId(loggedUser.id)
            RoleEnum.OWNER -> reservationRepository.findAllByApartmentOwnerId(loggedUser.id)
         }


        val list: MutableList<ReservationDTO> = mutableListOf()
        for (reservation in reservations) {
            list.add(
                ReservationDTO(
                    reservation.id,
                    reservation.startDate,
                    reservation.endDate,
                    UserDto(reservation.client.id, reservation.client.username, reservation.client.role),
                    reservation.currentStatus,
                    reservation.review?.text,
                    ApartmentSimpleDTO(
                        reservation.apartment.id,
                        reservation.apartment.name,
                        reservation.apartment.location
                    ),
                    reservation.price
                )
            )
        }
        return list
    }


    @Transactional
    fun deleteReservation(id: Long): Boolean {
        if (reservationRepository.findById(id).isPresent) {
            reservationRepository.deleteById(id)
            return true
        }
        throw Exception("There is no id")
    }


    fun showReview(reservationId: Long): ReviewDto {
        val reservation = reservationRepository.findById(reservationId).orElseThrow {
            NoSuchElementException("There is no reservation with this id")
        }
        return ReviewDto(reservation.review!!.id, reservation.review.text, reservation.review.rating)
    }


    @Transactional
    fun saveReview(reservationId: Long, reviewToSave: ReviewSaveDTO, principal: Principal): Long {

        val reservation = reservationRepository.findById(reservationId).orElseThrow {
            NoSuchElementException("There is no reservation with this id")
        }

        val loggedUser = userRepository.findByUsername(principal.name)

        if (reservation.client != loggedUser) {
            throw Exception("Only the client of the reservation can add review")
        } else if (reservation.currentStatus != StateStatusEnum.AWAITING_REVIEW) {
            throw Exception("Reservation is not in status: Awaiting review")
        } else {
            val review = Review(
                0,
                reviewToSave.text,
                reviewToSave.rating,
                LocalDateTime.now(),
                reservation
            )
            return reviewRepository.save(review).id
        }
    }

    @Transactional
    fun saveReservation(
        reservationToSave: ReservationSaveDto, principal: Principal
    ): Long {

        val loggedUser = userRepository.findByUsername(principal.name)

        if (RoleEnum.CLIENT != loggedUser.role) {
            throw Exception("You don't have authorities to do this functionality. Only Client can make reservation")
        }

        val apartment = apartmentRepository.findById(reservationToSave.apartmentId).orElseThrow {
            NoSuchElementException("There is no apartment with this id")
        }

        val possibilityToSave = (apartmentService.checkAvailability(
            reservationToSave.apartmentId,
            reservationToSave.startDate,
            reservationToSave.endDate
        ))

        if (possibilityToSave) {

            val reservation = Reservation(
                0,
                reservationToSave.startDate,
                reservationToSave.endDate,
                StateStatusEnum.UNDER_CONSIDERATION,
                LocalDateTime.now(),
                loggedUser,
                null,
                states = listOf(),
                apartment,
                calculatePrice(reservationToSave.startDate, reservationToSave.endDate, apartment.pricePerNight),
                reservationToSave.persons
            )

            reservationRepository.save(reservation)
            val newState = State(0, LocalDateTime.now(), reservation, StateStatusEnum.UNDER_CONSIDERATION)

            stateRepository.save(newState)

            return reservation.id

        } else {
            throw Exception("Apartment is not available on this period")
        }
    }

    fun changeStatus(reservationId: Long, newStatus: StateStatusEnum, principal: Principal): Long {
        val reservation = reservationRepository.findById(reservationId).orElseThrow {
            NoSuchElementException("There is no reservation with this id")
        }

        val loggedUser = userRepository.findByUsername(principal.name)

        if (loggedUser == reservation.apartment.manager || loggedUser == reservation.apartment.owner) {

            validateStatuses(newStatus, reservation.currentStatus)

            reservation.currentStatus = newStatus
            reservation.statusChangeDate = LocalDateTime.now()
            reservationRepository.save(reservation)

            val newState = State(0, LocalDateTime.now(), reservation, newStatus)

            return stateRepository.save(newState).id
        } else {
            throw Exception("You do not have the authority to change status of this reservation")
        }
    }

    private fun validateStatuses(newStatus: StateStatusEnum, currentStateStatus: StateStatusEnum) {
        if (newStatus == currentStateStatus) {
            throw Exception("Statuses can not be the same")
        } else if (newStatus == StateStatusEnum.BOOKED && currentStateStatus != StateStatusEnum.UNDER_CONSIDERATION) {
            throw Exception("You can not accept reservation in a status different from 'Under consideration'")
        } else if (newStatus == StateStatusEnum.OCCUPIED && currentStateStatus != StateStatusEnum.BOOKED) {
            throw Exception("You can not make check in when reservation is not in a status 'Booked'")
        } else if (newStatus == StateStatusEnum.AWAITING_REVIEW && currentStateStatus != StateStatusEnum.OCCUPIED) {
            throw Exception("You can not make check out when reservation is not in a status 'Occupied'")
        } else if (newStatus == StateStatusEnum.CLOSED) {
            throw Exception(
                "You can not change status of reservation to 'Closed'. " +
                        "Reservation will be closed when client will write a review."
            )
        }
    }

    companion object {
        fun calculatePrice(startDate: LocalDate, endDate: LocalDate, pricePerNight: Double): Double {
            return ChronoUnit.DAYS.between(startDate, endDate) * pricePerNight
        }
    }

}
package com.example.rentingapp.api.apartment

import com.example.rentingapp.api.apartment.dto.ApartmentDTO
import com.example.rentingapp.api.apartment.dto.PeriodDto
import com.example.rentingapp.api.reservation.dto.ReservationSimpleDto
import com.example.rentingapp.api.review.dto.ReviewDto
import com.example.rentingapp.api.user.dto.UserDto
import com.example.rentingapp.data.domain.Period
import com.example.rentingapp.data.repository.ApartmentRepository
import com.example.rentingapp.data.repository.PeriodRepository
import com.example.rentingapp.util.CommonUtil.Companion.checkIncludingPeriods
import com.example.rentingapp.util.CommonUtil.Companion.haveDateRangesOverlap
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.security.Principal
import java.time.LocalDate
import java.util.NoSuchElementException

@Service
class ApartmentService(val apartmentRepository: ApartmentRepository, val periodRepository: PeriodRepository) {

    fun getAll() = apartmentRepository.findAll().map {
        ApartmentDTO(
            it.id,
            it.name,
            it.location,
            UserDto(it.manager.id, it.manager.username, it.manager.role),
            UserDto(it.owner.id, it.owner.username, it.owner.role),
            it.periods?.map { period ->
                PeriodDto(
                    period.id, period.startDate, period.endDate
                )
            },
            it.reservations?.map { reservation ->
                ReservationSimpleDto(
                    reservation.id, reservation.startDate, reservation.endDate
                )
            },
            null,
            it.photo,
            it.pricePerNight
        )
    }

    fun checkAvailability(apartmentId: Long, startDate: LocalDate, endDate: LocalDate): Boolean {
        val apartment = apartmentRepository.findById(apartmentId).orElseThrow {
            NoSuchElementException("There is no apartment with this id")
        }

        val isChosenPeriodInRange = apartment.periods?.any { period ->
            checkIncludingPeriods(period.startDate, period.endDate, startDate, endDate)
        }

        return if (isChosenPeriodInRange != null && isChosenPeriodInRange) {
            val reservationInPeriod = apartment.reservations?.any { reservation ->
                haveDateRangesOverlap(reservation.startDate, reservation.endDate, startDate, endDate)
            };
            return reservationInPeriod == null || !reservationInPeriod
        } else {
            false
        }
    }

    @Transactional
    fun addAvailablePeriod(apartmentId: Long, startDate: LocalDate, endDate: LocalDate, principal: Principal): Long? {

        val apartment = apartmentRepository.findById(apartmentId).orElseThrow {
            NoSuchElementException("There is no apartment with this id")
        }

        if (apartment.owner.username != principal.name) {
            throw Exception("Only owner of the apartment can add available period")
        }

        if (apartment.periods!!.any { period ->
                haveDateRangesOverlap(
                    period.startDate, period.endDate, startDate, endDate
                )
            }) {
            throw Exception("Availability periods must not overlap!")
        }

        return periodRepository.save(Period(0, startDate = startDate, endDate = endDate, apartment = apartment)).id
    }

    @Transactional
    fun deleteAvailablePeriod(periodId: Long, principal: Principal) {

        val period = periodRepository.findById(periodId).orElseThrow() {
            throw Exception("There is no period with this ID")
        }

        if (period.apartment.owner.username != principal.name) {
            throw Exception("Only owner of the apartment can delete available period")
        }

        val doesPeriodOverlapReservation = period.apartment.reservations!!.any { reservation ->
            haveDateRangesOverlap(reservation.startDate, reservation.endDate, period.startDate, period.endDate)
        }

        if (doesPeriodOverlapReservation) {
            throw Exception("Deleting Period is overlaping reservation")
        } else {
            periodRepository.deleteById(periodId)
        }
    }

    fun getApartment(apartmentId: Long): ApartmentDTO {
        val apartment = apartmentRepository.findById(apartmentId).orElseThrow {
            NoSuchElementException("There is no apartment with this id")
        }

        val reviews = apartment.reservations?.mapNotNull { reservation ->
            reservation.review?.let { review ->
                ReviewDto(
                    review.id, review.text, review.rating
                )
            }
        } ?: emptyList()

        return ApartmentDTO(
            apartment.id,
            apartment.name,
            apartment.location,
            UserDto(apartment.manager.id, apartment.manager.username, apartment.manager.role),
            UserDto(apartment.owner.id, apartment.owner.username, apartment.owner.role),
            apartment.periods!!.map { period ->
                PeriodDto(
                    period.id, period.startDate, period.endDate
                )
            },
            apartment.reservations!!.map { reservation ->
                ReservationSimpleDto(
                    reservation.id, reservation.startDate, reservation.endDate
                )
            },
            reviews,
            apartment.photo,
            apartment.pricePerNight
        )
    }

}
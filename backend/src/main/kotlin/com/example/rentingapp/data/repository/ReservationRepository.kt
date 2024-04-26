package com.example.rentingapp.data.repository

import com.example.rentingapp.data.domain.Reservation
import org.springframework.data.repository.CrudRepository

interface ReservationRepository : CrudRepository<Reservation, Long> {

    fun findAllByClientId(clientId: Long?): List<Reservation>

    fun findAllByApartmentManagerId(clientId: Long?): List<Reservation>
    fun findAllByApartmentOwnerId(clientId: Long?): List<Reservation>
}
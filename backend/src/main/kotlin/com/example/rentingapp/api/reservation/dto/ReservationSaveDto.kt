package com.example.rentingapp.api.reservation.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(name = "Reservation to save")
data class ReservationSaveDto(
    @Schema(name = "Apartment identifier")
    val apartmentId: Long,

    @Schema(name = "Reservation start date")
    val startDate: LocalDate,

    @Schema(name = "Reservation end date")
    val endDate: LocalDate,

    @Schema(name = "Number of persons")
    val persons: Long
)


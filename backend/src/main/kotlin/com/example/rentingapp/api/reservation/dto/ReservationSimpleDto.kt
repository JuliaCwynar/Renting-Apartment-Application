package com.example.rentingapp.api.reservation.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(name = "Reservation Simple")
data class ReservationSimpleDto(
    @Schema(name = "Reservation identifier")
    val reservationId: Long,

    @Schema(name = "Reservation start date")
    val startDate: LocalDate,

    @Schema(name = "Reservation end date")
    val endDate: LocalDate
)

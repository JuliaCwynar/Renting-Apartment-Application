package com.example.rentingapp.api.reservation.dto

import com.example.rentingapp.api.apartment.dto.ApartmentSimpleDTO
import com.example.rentingapp.api.user.dto.UserDto
import com.example.rentingapp.data.domain.StateStatusEnum
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(name = "Reservation")
data class ReservationDTO(
    @Schema(name = "Reservation identifier")
    val id : Long,

    @Schema(name = "Reservation start date")
    var startDate : LocalDate,

    @Schema(name = "Reservation end date")
    var endDate : LocalDate,

    @Schema(name = "Client")
    var client: UserDto,

    @Schema(name = "Current reservation State")
    var currentStatus: StateStatusEnum,

    @Schema(name = "Review text")
    var review: String?,

    @Schema(name = "Apartment ")
    var apartment: ApartmentSimpleDTO,

    @Schema(name = "Full reservation price")
    var price: Double
)


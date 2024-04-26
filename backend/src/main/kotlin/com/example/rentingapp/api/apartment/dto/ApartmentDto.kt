package com.example.rentingapp.api.apartment.dto

import com.example.rentingapp.api.reservation.dto.ReservationSimpleDto
import com.example.rentingapp.api.review.dto.ReviewDto
import com.example.rentingapp.api.user.dto.UserDto
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Apartment Details")
data class ApartmentDTO(

    @Schema(name = "Apartment identifier")
    val id: Long,

    @Schema(name = "Apartment name")
    val name: String,

    @Schema(name = "Apartment location")
    val location: String,

    @Schema(name = "Apartment manager")
    val manager: UserDto,

    @Schema(name = "Apartment owner")
    val owner: UserDto,

    @Schema(name = "List of available periods")
    val periods: List<PeriodDto>?,

    @Schema(name = "List of reservations")
    val reservations: List<ReservationSimpleDto>?,

    @Schema(name = "List of reviews")
    val reviews: List<ReviewDto>?,

    @Schema(name = "Photo url")
    val photo: String?,

    @Schema(name = "Price")
    val pricePerNight: Double
)
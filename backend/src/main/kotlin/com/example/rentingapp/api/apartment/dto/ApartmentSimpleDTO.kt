package com.example.rentingapp.api.apartment.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Apartment Simple")
data class ApartmentSimpleDTO(

    @Schema(name = "Apartment identifier")
    val id: Long,
    @Schema(name = "Apartment name")
    val name: String,
    @Schema(name = "Apartment location")
    val location: String
)

package com.example.rentingapp.api.review.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Review to Save")
data class ReviewSaveDTO(

    @Schema(name = "Review text")
    val text : String,

    @Schema(name = "Review rating")
    val rating : Long
)
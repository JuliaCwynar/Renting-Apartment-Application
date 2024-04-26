package com.example.rentingapp.api.review.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Review")
data class ReviewDto(
    @Schema(name = "Review identifier")
    val id: Long,

    @Schema(name = "Review text")
    val text : String,

    @Schema(name = "Review raiting")
    val rating : Long
)
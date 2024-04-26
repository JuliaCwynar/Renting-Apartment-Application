package com.example.rentingapp.api.apartment.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(name = "Period")
data class PeriodDto(

    @Schema(name = "Period identifier")
    val id: Long,

    @Schema(name = "Period start date")
    val startDate: LocalDate,

    @Schema(name = "Period end date")
    val endDate: LocalDate
)

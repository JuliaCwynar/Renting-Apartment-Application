package com.example.rentingapp.api.user.dto

import com.example.rentingapp.data.domain.RoleEnum
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "User")
data class UserDto(

    @Schema(name = "User identifier")
    val id: Long,

    @Schema(name = "User name")
    val username: String,

    @Schema(name = "User role")
    val role: RoleEnum

    )
package com.example.rentingapp.api.user.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "User login")
data class UserLoginDto(

    @Schema(name = "User username")
    val username: String,

    @Schema(name = "User password")
    val password: String,

)

package com.example.rentingapp.api.user

import com.example.rentingapp.api.user.dto.UserDto
import com.example.rentingapp.api.user.dto.UserLoginDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "User API")
class UserController(val userService: UserService) {

    @PostMapping
    @Operation(summary = "Log into application")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Login into app"),
            ApiResponse(responseCode = "401", description = "Unauthorized: Wrong Credentials"),
            ApiResponse(responseCode = "500", description = "Internal server error")]
    )
    fun authenticateUser(
        @RequestBody loginDto: UserLoginDto, principal: Principal
    ): ResponseEntity<*> = userService.login(loginDto)

}


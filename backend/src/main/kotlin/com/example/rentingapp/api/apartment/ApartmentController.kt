package com.example.rentingapp.api.apartment

import com.example.rentingapp.api.apartment.dto.ApartmentDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.time.LocalDate

@RestController
@RequestMapping("/api/apartment")
@Tag(name = "Apartments", description = "Apartments API")
class ApartmentController(val service: ApartmentService) {

    @GetMapping
    @Operation(summary = "Get all apartments")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "The list of apartments that are in the system"),
            ApiResponse(responseCode = "500", description = "Internal server error")]
    )
    fun findAll(): List<ApartmentDTO> = service.getAll()

    @GetMapping("{apartmentId}")
    @Operation(summary = "Get apartment details")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Apartment with details"),
            ApiResponse(responseCode = "500", description = "Internal server error")]
    )
    fun getApartment(@PathVariable apartmentId: Long): ApartmentDTO = service.getApartment(apartmentId)


    @PostMapping("{id}/period")
    @Operation(summary = "Add available period to the apartment")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Available period added correctly"), 
            ApiResponse(responseCode = "500", description = "Internal server error")]
    )
    fun addAvailablePeriod(
        @PathVariable id: Long,
        @RequestParam startDate: LocalDate,
        @RequestParam endDate: LocalDate,
        principal: Principal
    ): Long? = service.addAvailablePeriod(id, startDate, endDate, principal)

    @DeleteMapping("period/{id}")
    @Operation(summary = "Delete available period to the apartment")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Period deleted correctly"), 
            ApiResponse(responseCode = "500", description = "Internal server error")]
    )
    fun deleteAvailablePeriod(@PathVariable("id") periodId: Long, principal: Principal) = service.deleteAvailablePeriod(periodId, principal)

    @GetMapping("{id}/check")
    @Operation(summary = "Check availability")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "The list of apartments that are in the system"
        ), ApiResponse(responseCode = "500", description = "Internal server error")]
    )
    fun checkAvailability(
        @PathVariable("id") apartmentId: Long, @RequestParam(required = true) startDate: LocalDate, @RequestParam(required = true) endDate: LocalDate
    ): Boolean = service.checkAvailability(apartmentId, startDate, endDate)

}
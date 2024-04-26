package com.example.rentingapp.api.reservation

import com.example.rentingapp.api.reservation.dto.ReservationDTO
import com.example.rentingapp.api.reservation.dto.ReservationSaveDto
import com.example.rentingapp.api.review.dto.ReviewDto
import com.example.rentingapp.api.review.dto.ReviewSaveDTO
import com.example.rentingapp.data.domain.StateStatusEnum
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.security.Principal


@RestController
@RequestMapping("/api/reservation")
@Tag(name = "Reservations", description = "Reservations API")
class ReservationController(val service: ReservationService) {


    @PostMapping
    @Operation(summary = "Save Reservation")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Reservation saved correctly"),
            ApiResponse(responseCode = "500", description = "Internal server error")]
    )
    fun saveReservation(
        @RequestBody reservationToSave: ReservationSaveDto, principal: Principal
    ): Long = service.saveReservation(reservationToSave, principal)


    @PostMapping("{reservationId}/review")
    @Operation(summary = "Save Review")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Review saved correctly"),
            ApiResponse(responseCode = "500", description = "Internal server error")]
    )
    fun saveReview(
        @RequestBody reviewToSave: ReviewSaveDTO,
        @PathVariable reservationId: Long,
        principal: Principal
    ): Long = service.saveReview(reservationId, reviewToSave, principal)

    @GetMapping("{reservationId}/review")
    @Operation(summary = "Show Review")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Review showed correctly"),
            ApiResponse(responseCode = "500", description = "Internal server error")]
    )
    fun getReview(@PathVariable reservationId: Long): ReviewDto = service.showReview(reservationId)


    @DeleteMapping("{id}")
    @Operation(summary = "Delete Reservation")
    @ApiResponses(
        value = [ApiResponse(responseCode = "200", description = "Reservation deleted correctly"),
            ApiResponse(responseCode = "500", description = "Interval Server Error")]
    )
    fun deleteReservation(@PathVariable id: Long): Boolean = service.deleteReservation(id)

    @PutMapping("{reservationId}/change-status")
    @Operation(summary = "Change current status of the reservation")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Status changed"),
            ApiResponse(responseCode = "500", description = "Interval Server Error")]
    )
    fun changeStatus(
        @PathVariable reservationId: Long,
        @RequestParam newStatus: StateStatusEnum,
        principal: Principal
    )
            : Long = service.changeStatus(reservationId, newStatus, principal)


    @GetMapping("{id}/details")
    @Operation(summary = "Reservation Details")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Details of an reservation"),
            ApiResponse(responseCode = "500", description = "Internal Server Error")]
    )
    fun reservationDetails(@PathVariable id: Long): ReservationDTO = service.getDetails(id)

    @GetMapping("/list")
    @Operation(summary = "List of reservations of logged user")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "List of reservations"
        ), ApiResponse(
            responseCode = "500",
            description = "Internal Server Error"
        )]
    )
    fun showReservations(principal: Principal): List<ReservationDTO> = service.showReservations(principal)

}
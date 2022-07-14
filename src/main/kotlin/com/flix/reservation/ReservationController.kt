package com.flix.reservation

import com.flix.reservation.dto.ReservationDTO
import com.flix.reservation.dto.ReservationResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/reservation")
class ReservationController(
    private val reservationService: ReservationService
) {

    @PostMapping
    fun createReservation(@RequestBody dto: ReservationDTO): ResponseEntity<ReservationResponseDTO> {
        val response = reservationService.createReservation(dto.customerId, dto.tripId, dto.numberOfSpots)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PostMapping(path = ["/{reservationId}/cancel"])
    fun cancelReservation(@PathVariable reservationId: UUID): ResponseEntity<Void> {
        reservationService.cancelReservation(reservationId)
        return ResponseEntity.status(HttpStatus.ACCEPTED).build()
    }
}
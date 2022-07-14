package com.flix.reservation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/admin/reservation")
class ReservationAdminController(private val reservationService: ReservationService) {

    @GetMapping()
    fun searchReservations(
        @RequestParam customerId: UUID? = null,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ) = ResponseEntity.ok(reservationService.findReservation(customerId, page, size))

}
package com.flix.reservation.dto

import com.flix.reservation.domain.ReservationSpotStatus
import com.flix.reservation.domain.ReservationStatus
import java.util.UUID

data class ReservationDTO(
    val tripId: UUID,
    val numberOfSpots: Int,
    // TODO Extract customerId form authentication token
    // For simplicity pass it in the DTO for now
    val customerId: UUID
)

data class ReservationResponseDTO(
    val reservationId:UUID,
    val bookingId: UUID,
    val spotIds: List<UUID>
)

data class AdminReservationResponseDTO(
    val reservationId:UUID,
    val bookingId: UUID,
    val customerId: UUID,
    val status: ReservationStatus,
    val spotIds: List<AdminReservationSpotResponseDTO>
)

data class AdminReservationSpotResponseDTO(
    val spotId: UUID,
    val status: ReservationSpotStatus
)
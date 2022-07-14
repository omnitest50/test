package com.flix.reservation

import com.flix.reservation.domain.Reservation
import com.flix.reservation.domain.ReservationSpot
import com.flix.reservation.domain.Spot
import com.flix.reservation.domain.Trip
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

internal class ReservationServiceTest {

    private val tripRepository = mockk<TripRepository>()
    private val reservationRepository = mockk<ReservationRepository>()

    private val reservationService = ReservationService(
        tripRepository,
        reservationRepository
    )

    @Test
    fun `Create Reservation`() {
        val tripId = UUID.randomUUID()
        val customerId = UUID.randomUUID()
        val trip = Trip(availableSpots = 1).apply {
            spots = mutableListOf(Spot(trip = this))
        }
        val reservation = Reservation(
            bookingId = UUID.randomUUID(),
            customerId = customerId,
            trip = trip
        ).apply {
            reservationSpots = trip.spots.map {
                ReservationSpot(spotId = it.id, reservation = this)
            }
        }
        every { tripRepository.findTripById(tripId) } returns trip
        every { tripRepository.save(any()) } returns trip
        every { reservationRepository.save(any()) } returns reservation

        val result = reservationService.createReservation(customerId, tripId, 1)

        assertNotNull(result.reservationId)
        assertNotNull(result.bookingId)
        assertEquals(result.spotIds.size, 1)
    }

    @Test
    fun `Create Reservation Throws InsufficientTripSpotException`() {
        val tripId = UUID.randomUUID()
        val customerId = UUID.randomUUID()
        val trip = Trip(availableSpots = 1).apply {
            spots = mutableListOf(Spot(trip = this))
        }

        every { tripRepository.findTripById(tripId) } returns trip

        assertThrows<InsufficientTripSpotException> { reservationService.createReservation(customerId, tripId, 2) }
    }
}
package com.flix.reservation

import com.flix.reservation.domain.Reservation
import com.flix.reservation.domain.Trip
import com.flix.reservation.domain.ReservationSpot
import com.flix.reservation.domain.ReservationSpotStatus
import com.flix.reservation.domain.ReservationStatus
import com.flix.reservation.dto.AdminReservationResponseDTO
import com.flix.reservation.dto.AdminReservationSpotResponseDTO
import com.flix.reservation.dto.ReservationResponseDTO
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.UUID
import javax.transaction.Transactional

@Service
class ReservationService(
    private val tripRepository: TripRepository,
    private val reservationRepository: ReservationRepository
) {

    /**
     *  TODO Extract customerId form authentication token, For simplicity pass it as parameter for now.
     */
    @Transactional
    fun createReservation(customerId: UUID, tripId: UUID, numberOfSpots: Int): ReservationResponseDTO {
        val trip = tripRepository.findTripById(tripId)?.apply {
            if (availableSpots < numberOfSpots)
                throw InsufficientTripSpotException("All spots are already reserved!")

            availableSpots -= numberOfSpots
            tripRepository.save(this)

        } ?: throw TripNotFoundException("Trip with Id: $tripId not found!")

        val availableSpots = getAndReserveAvailableSpots(trip, numberOfSpots)

        val reservation = Reservation(
            bookingId = UUID.randomUUID(),
            customerId = customerId,
            trip = trip
        ).apply {
            reservationSpots = availableSpots.map {
                ReservationSpot(spotId = it.id, reservation = this)
            }
        }

        reservationRepository.save(reservation)

        return ReservationResponseDTO(
            reservation.id,
            reservation.bookingId,
            reservation.reservationSpots.map { it.spotId })
    }

    @Transactional
    fun cancelReservation(reservationId: UUID) {
        val reservation = reservationRepository.findReservationById(reservationId)?.apply {
            if (status == ReservationStatus.CANCELED)
                throw BadRequest("Reservation with Id: $reservationId is already canceled")

            status = ReservationStatus.CANCELED
            reservationSpots.onEach { spot ->
                spot.status = ReservationSpotStatus.CANCELED
            }
            reservationRepository.save(this)
        } ?: throw ReservationNotFoundException("Reservation with Id: $reservationId not found!")

        tripRepository.findTripById(reservation.tripId)?.apply {
            this.availableSpots += reservation.reservationSpots.size

            this.spots.filter { spot -> reservation.reservationSpots.any { it.spotId == spot.id } }
                .onEach { it.isReserved = false }

            tripRepository.save(this)
        } ?: throw TripNotFoundException("Trip with Id: ${reservation.tripId} not found!")
    }

    private fun getAndReserveAvailableSpots(trip: Trip, numberOfSpots: Int) = trip.spots
        .filter { !it.isReserved }
        .take(numberOfSpots)
        .onEach {
            it.isReserved = true
        }

    fun findReservation(customerId: UUID?, page: Int, size: Int): List<AdminReservationResponseDTO> {
        val reservations = customerId?.let {
            reservationRepository.findAllByCustomerId(customerId, PageRequest.of(page, size))
        } ?: reservationRepository.findAll(PageRequest.of(page, size))

        return reservations.map {
            AdminReservationResponseDTO(
                it.id,
                it.bookingId,
                it.customerId,
                it.status,
                it.reservationSpots.map { spot -> AdminReservationSpotResponseDTO(spot.spotId, spot.status) })
        }
    }
}

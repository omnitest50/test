package com.flix.reservation.domain

import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.OneToMany
import javax.persistence.EnumType
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.ManyToOne
import javax.persistence.JoinColumn

@Entity
class Reservation(
    @Id
    val id: UUID = UUID.randomUUID(),
    var bookingId: UUID,
    var customerId: UUID,
    @OneToOne
    var trip: Trip,
    @Enumerated(EnumType.STRING)
    var status: ReservationStatus = ReservationStatus.CONFIRMED,
    val createdDate: OffsetDateTime = OffsetDateTime.now(),
    var updatedDate: OffsetDateTime = OffsetDateTime.now()
) {
    @OneToMany(mappedBy = "reservation", cascade = [CascadeType.ALL])
    var reservationSpots: List<ReservationSpot> = emptyList()

    @Column(name = "trip_id", insertable= false, updatable = false)
    lateinit var tripId: UUID
}

@Entity
class ReservationSpot(
    @Id
    val id: UUID = UUID.randomUUID(),
    val spotId: UUID,
    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    val reservation: Reservation,
    @Enumerated(EnumType.STRING)
    var status: ReservationSpotStatus = ReservationSpotStatus.CONFIRMED,
    val createdDate: OffsetDateTime = OffsetDateTime.now(),
)

enum class ReservationStatus {
    CANCELED,
    CONFIRMED,
}

enum class ReservationSpotStatus {
    CANCELED,
    CONFIRMED,
}
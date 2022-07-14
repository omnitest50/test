package com.flix.reservation.domain

import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Trip(
    @Id
    val id: UUID = UUID.randomUUID(),
    var title: String? = null,
    var availableSpots: Int,
    val createdDate: OffsetDateTime = OffsetDateTime.now(),
    var updatedDate: OffsetDateTime = OffsetDateTime.now()
) {
    @OneToMany(mappedBy = "trip")
    var spots: List<Spot> = emptyList()
}

@Entity
class Spot(
    @Id
    val id: UUID = UUID.randomUUID(),
    @ManyToOne
    @JoinColumn(name = "tripId", insertable = false)
    var trip: Trip,
    var isReserved: Boolean = false,
    val createdDate: OffsetDateTime = OffsetDateTime.now(),
    var updatedDate: OffsetDateTime = OffsetDateTime.now()
)
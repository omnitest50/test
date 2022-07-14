package com.flix.reservation

import com.flix.reservation.domain.Spot
import com.flix.reservation.domain.Trip
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository
import java.util.UUID
import javax.persistence.LockModeType

@Repository
interface TripRepository: JpaRepository<Trip, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findTripById(id: UUID): Trip?
}

@Repository
interface SpotRepository: JpaRepository<Spot, UUID>
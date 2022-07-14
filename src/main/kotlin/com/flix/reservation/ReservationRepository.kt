package com.flix.reservation

import com.flix.reservation.domain.Reservation
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository
import java.util.UUID
import javax.persistence.LockModeType

@Repository
interface ReservationRepository: JpaRepository<Reservation, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findReservationById(id: UUID): Reservation?

    fun findAllByCustomerId(customerId: UUID, pageable: Pageable): List<Reservation>
}
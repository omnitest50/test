package com.flix.reservation

open class BusinessException(message: String?) : Throwable(message)
class TripNotFoundException(message: String?) : BusinessException(message)
class ReservationNotFoundException(message: String?) : BusinessException(message)
class BadRequest(message: String?) : BusinessException(message)
class InsufficientTripSpotException(message: String) : BusinessException(message)
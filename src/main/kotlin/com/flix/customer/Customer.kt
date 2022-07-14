package com.flix.customer

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Customer(
    @Id
    val id: UUID,
    var name: String
)
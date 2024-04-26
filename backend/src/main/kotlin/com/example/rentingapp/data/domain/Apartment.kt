package com.example.rentingapp.data.domain

import jakarta.persistence.Entity
import jakarta.persistence.*

@Entity
@Table(schema = "rentingapartment", name = "apartment")
data class Apartment(

    @Id
    @GeneratedValue
    val id: Long,

    val location: String,

    val name: String,

    val photo: String,

    val pricePerNight: Double,

    @ManyToOne val manager: User,

    @ManyToOne val owner: User,

    @OneToMany(mappedBy = "apartment")
    var periods: List<Period>?,

    @OneToMany(mappedBy = "apartment")
    val reservations: List<Reservation>?
)

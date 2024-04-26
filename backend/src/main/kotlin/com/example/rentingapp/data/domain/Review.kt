package com.example.rentingapp.data.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(schema = "rentingapartment", name = "review")
data class Review(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val text: String,

    val rating: Long,

    val date: LocalDateTime,

    @OneToOne
    val reservation: Reservation
)
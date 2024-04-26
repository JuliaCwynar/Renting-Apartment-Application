package com.example.rentingapp.data.domain

import com.example.rentingapp.data.converter.StateStatusConverter
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(schema = "rentingapartment", name = "state")
data class State(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val date: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    val reservation: Reservation,

    @Convert(converter = StateStatusConverter::class)
    val status: StateStatusEnum

    )

package com.example.rentingapp.data.domain

import com.example.rentingapp.data.converter.StateStatusConverter
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(schema = "rentingapartment", name = "reservation")
data class Reservation(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val startDate: LocalDate,

    val endDate: LocalDate,
    @Convert(converter = StateStatusConverter::class)
    var currentStatus: StateStatusEnum,

    var statusChangeDate: LocalDateTime,

    @ManyToOne val client: User,

    @OneToOne( mappedBy = "reservation", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val review: Review?,

    @OneToMany( mappedBy = "reservation", cascade = [CascadeType.ALL], orphanRemoval = true)
    val states: List<State>,

    @ManyToOne
    @JoinColumn(name = "apartmentId")
    val apartment: Apartment,

    val price: Double,

    val persons: Long

    )


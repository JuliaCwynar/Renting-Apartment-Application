package com.example.rentingapp.data.domain

import java.time.LocalDate
import jakarta.persistence.*

@Entity
@Table(schema = "rentingapartment", name = "period")
data class Period (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val startDate: LocalDate,

    val endDate: LocalDate,

    @ManyToOne
    @JoinColumn(name = "apartmentId")
    val apartment: Apartment
)
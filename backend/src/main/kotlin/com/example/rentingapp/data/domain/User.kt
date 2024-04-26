package com.example.rentingapp.data.domain

import com.example.rentingapp.data.converter.RoleEnumConverter
import com.example.rentingapp.data.converter.StateStatusConverter
import jakarta.persistence.*

@Entity
@Table(schema = "rentingapartment", name = "user")
data class User(

    @Id
    @GeneratedValue
    val id: Long,

    val username: String,

    @Convert(converter = RoleEnumConverter::class)
    val role: RoleEnum,

    val password: String
)
package com.example.rentingapp.data.repository

import com.example.rentingapp.data.domain.Apartment
import org.springframework.data.repository.CrudRepository

//@Repository
interface ApartmentRepository : CrudRepository<Apartment, Long>

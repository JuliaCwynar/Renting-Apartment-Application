package com.example.rentingapp.data.repository

import com.example.rentingapp.data.domain.State
import org.springframework.data.repository.CrudRepository

interface StateRepository : CrudRepository<State, Long> {
}
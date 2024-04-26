package com.example.rentingapp.data.repository

import com.example.rentingapp.data.domain.Period
import org.springframework.data.repository.CrudRepository
interface PeriodRepository : CrudRepository<Period, Long> {
}
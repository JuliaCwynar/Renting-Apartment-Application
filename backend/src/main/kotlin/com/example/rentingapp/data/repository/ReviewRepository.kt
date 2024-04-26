package com.example.rentingapp.data.repository

import com.example.rentingapp.data.domain.Review
import org.springframework.data.repository.CrudRepository


interface ReviewRepository : CrudRepository<Review, Long> {
}
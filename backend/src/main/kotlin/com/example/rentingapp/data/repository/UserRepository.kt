package com.example.rentingapp.data.repository

import com.example.rentingapp.data.domain.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {

     fun findByUsername(username: String?): User

}

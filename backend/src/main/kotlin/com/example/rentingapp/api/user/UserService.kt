package com.example.rentingapp.api.user

import com.example.rentingapp.api.user.dto.UserDto
import com.example.rentingapp.api.user.dto.UserLoginDto
import com.example.rentingapp.data.domain.User
import com.example.rentingapp.data.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserService(val userRepository: UserRepository){

    fun login(loginDto:UserLoginDto): ResponseEntity<*> {

       val user = userRepository.findByUsername(loginDto.username)

        return if(BCrypt.checkpw(loginDto.password, user.password)) {
            ResponseEntity(UserDto(user.id, user.username, user.role), HttpStatus.OK)
        } else {
            ResponseEntity("There is no User with this username and password!...", HttpStatus.UNAUTHORIZED)
        }

    }
    fun findUserById(username: String?): Optional<User> {
        return Optional.of(userRepository.findByUsername(username))
    }

}
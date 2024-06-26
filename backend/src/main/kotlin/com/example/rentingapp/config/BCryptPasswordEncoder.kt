package com.example.rentingapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class BCryptPasswordEncoder {

    @Bean
    fun passwordEncoderAndMatcher(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
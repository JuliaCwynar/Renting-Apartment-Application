package com.example.rentingapp.config

import com.example.rentingapp.api.user.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.stereotype.Service
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val passwordEncoderAndMatcher: PasswordEncoder,
    val myUserDetailsService: MyUserDetailsService
) {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeRequests {
                authorize("/swagger-ui.html", permitAll)
                authorize("/api/apartment", permitAll)
                authorize(anyRequest, authenticated)
            }
            formLogin { }
            httpBasic { }
            cors { }
        }
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:5173")
        configuration.allowedMethods = listOf("GET", "POST", "OPTIONS")
        configuration.allowCredentials = true
        configuration.allowedHeaders = listOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Throws(java.lang.Exception::class)
    protected fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(myUserDetailsService)
            .passwordEncoder(passwordEncoderAndMatcher)
    }
}

@Service
class MyUserDetailsService(val users: UserService) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? {
        val user = users.findUserById(username).orElseThrow { Exception("There is no user with this username") }
        val roles: MutableList<GrantedAuthority> = mutableListOf()
        roles.add(SimpleGrantedAuthority(user.role.code))
        return User(user.username, user.password, roles)
    }
}
package com.example.rentingapp

import com.example.rentingapp.api.apartment.ApartmentService
import com.example.rentingapp.api.reservation.ReservationService
import com.example.rentingapp.data.domain.Apartment
import com.example.rentingapp.data.domain.Period
import com.example.rentingapp.data.domain.RoleEnum
import com.example.rentingapp.data.domain.User
import com.example.rentingapp.data.repository.ApartmentRepository
import com.example.rentingapp.data.repository.PeriodRepository
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
@Transactional
class RentingAppApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Autowired
    private lateinit var apartmentService: ApartmentService

    @Autowired
    private lateinit var apartmentRepository: ApartmentRepository

    @Autowired
    private lateinit var periodRepository: PeriodRepository


    @Test
    fun testApartmentPriceCalculation() {
        val apartment = Apartment(333, "Portugal", "Apartment Portugal", "photoUrl.pl", 15.5,
            User(444,"manager", RoleEnum.MANAGER, "pass123"),
            User(555, "owner", RoleEnum.OWNER, "pass123"), null, null)
        Assertions.assertEquals(
            77.5,
            ReservationService.calculatePrice(
                LocalDate.of(2023, 7, 1),
                LocalDate.of(2023, 7, 6),
                apartment.pricePerNight
            )
        )
    }



    @Test
    fun testGetAllApartments() {
        val user = User(id = 1L, username = "john_doe", role = RoleEnum.OWNER, password = "password123")
        val manager = User(id = 2L, username = "manager_user", role = RoleEnum.MANAGER, password = "managerpass")
        val apartment1 = Apartment(id = 1L, location = "Location 1", name = "Apartment 1", "photoUrl.pl",55.5, manager, user, null, null)
        val apartment2 = Apartment(id = 2L, location = "Location 2", name = "Apartment 2",  "photoUrl.pt",44.3, manager, user, null, null)
        apartmentRepository.save(apartment1)
        apartmentRepository.save(apartment2)

        val result = apartmentService.getAll()

        org.assertj.core.api.Assertions.assertThat(result).hasSize(2)
        org.assertj.core.api.Assertions.assertThat(result[0].name).isEqualTo("Apartment 1")
        org.assertj.core.api.Assertions.assertThat(result[1].name).isEqualTo("Apartment 2")
    }

    @Test
    fun testCheckAvailability() {
        val user = User(id = 1L, username = "john_doe", role = RoleEnum.OWNER, password = "password123")
        val manager = User(id = 2L, username = "manager_user", role = RoleEnum.MANAGER, password = "managerpass")
        val apartment = Apartment(id = 1L, location = "Lokalizacja 1", name = "Apartment 1","photoUrl.pl", 55.5, manager, user, listOf(), null)

        apartmentRepository.save(apartment)

        val startDate = LocalDate.now().plusDays(1)
        val endDate = startDate.plusDays(5)
        val period = Period(id = 1L, startDate = startDate, endDate = endDate, apartment)
        periodRepository.save(period)

        apartment.periods = listOf(period)

        apartmentRepository.save(apartment)

        val isAvailable = apartmentService.checkAvailability(apartment.id, startDate, endDate)

        org.assertj.core.api.Assertions.assertThat(isAvailable).isTrue
    }


}

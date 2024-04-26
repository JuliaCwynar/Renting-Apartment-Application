package com.example.rentingapp.util

import java.time.LocalDate

/**
 * Util class.
 */
class CommonUtil {
    companion object {
        fun checkIncludingPeriods(
            firstPeriodStart: LocalDate, firstPeriodEnd: LocalDate,
            secondPeriodStart: LocalDate, secondPeriodEnd: LocalDate
        ): Boolean {
            return firstPeriodEnd >= secondPeriodEnd && firstPeriodStart <= secondPeriodStart
        }

        fun haveDateRangesOverlap(
            startDate1: LocalDate, endDate1: LocalDate,
            startDate2: LocalDate, endDate2: LocalDate
        ): Boolean {
            return startDate1.isBefore(endDate2) && endDate1.isAfter(startDate2)
        }

    }
}

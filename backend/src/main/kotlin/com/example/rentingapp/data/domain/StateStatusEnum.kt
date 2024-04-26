package com.example.rentingapp.data.domain

enum class StateStatusEnum(val code:String) {
    AVAILABLE("A"),
    UNDER_CONSIDERATION("U"),
    BOOKED("B"),
    OCCUPIED("O"),
    AWAITING_REVIEW("R"),
    CLOSED("C");

    companion object {
        private val MAP = values().associateBy(StateStatusEnum::code)

        fun parse(input: String): StateStatusEnum? {
            return MAP[input]
        }
    }
}
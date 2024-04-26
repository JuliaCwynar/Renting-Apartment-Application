package com.example.rentingapp.data.domain

enum class RoleEnum(val code: String) {
    MANAGER("M"),
    OWNER("O"),
    CLIENT("C");

    companion object {
        private val MAP = RoleEnum.values().associateBy(RoleEnum::code)

        fun parse(input: String): RoleEnum? {
            return MAP[input]
        }
    }
}
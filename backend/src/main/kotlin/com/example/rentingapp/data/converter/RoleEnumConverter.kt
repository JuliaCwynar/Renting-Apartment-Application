package com.example.rentingapp.data.converter

import com.example.rentingapp.data.domain.RoleEnum
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class RoleEnumConverter: AttributeConverter<RoleEnum, String> {

    override fun convertToDatabaseColumn(role: RoleEnum): String {
        return role.name
    }

    override fun convertToEntityAttribute(code: String): RoleEnum? {
        return RoleEnum.parse(code)
    }
}
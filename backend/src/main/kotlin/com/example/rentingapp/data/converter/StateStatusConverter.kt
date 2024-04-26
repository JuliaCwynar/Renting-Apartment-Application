package com.example.rentingapp.data.converter

import com.example.rentingapp.data.domain.StateStatusEnum
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class StateStatusConverter : AttributeConverter<StateStatusEnum, String> {

    override fun convertToDatabaseColumn(stateStatus: StateStatusEnum): String {
        return stateStatus.code
    }

    override fun convertToEntityAttribute(code: String): StateStatusEnum? {
        return StateStatusEnum.parse(code)
    }
}
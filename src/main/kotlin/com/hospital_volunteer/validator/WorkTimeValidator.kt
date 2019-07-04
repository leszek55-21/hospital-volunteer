package com.hospital_volunteer.validator

import com.hospital_volunteer.dto.WorkTimeSaveDTO
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import lombok.extern.slf4j.Slf4j
import java.time.DateTimeException
import java.time.LocalDateTime
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.ValidationException

@Slf4j
class WorkTimeValidator : ConstraintValidator<CorrectWorkTime, Any> {
    override fun isValid(value: Any, context: ConstraintValidatorContext): Boolean {

        if (value is WorkTimeSaveDTO) {
            return isNotPastDate(value)
                    && isValidHour(value.startHour)
                    && isCorrectNumberOfHours(value.numberOfHours)
        } else {
            throw ValidationException("Validation not supported")
        }
    }

    private fun isNotPastDate(workTimeSaveDTO: WorkTimeSaveDTO): Boolean {
        if(isValidMonthAndDayValue(workTimeSaveDTO.year, workTimeSaveDTO.month, workTimeSaveDTO.day, workTimeSaveDTO.startHour)) {
            val sentDate = LocalDateTime.of(workTimeSaveDTO.year, workTimeSaveDTO.month, workTimeSaveDTO.day,
                    workTimeSaveDTO.startHour, 0, 0)
            return sentDate.isAfter(LocalDateTime.now())
        }
        return false
    }

    private fun isValidHour(hourFrom: Int): Boolean {
        return hourFrom in 0..24
    }

    private fun isCorrectNumberOfHours(numberOfHours: Int): Boolean {
        return numberOfHours > 0
    }

    private fun isValidMonthAndDayValue(yearValue: Int, monthValue: Int, dayValue: Int, startHour: Int): Boolean {
        try {
            LocalDateTime.of(yearValue, monthValue,dayValue, startHour,0)
        } catch (e: DateTimeException){
            log.println("Incorrect date provided")
            return false
        }
        return monthValue in 1..12 && dayValue in 1..31
    }


}

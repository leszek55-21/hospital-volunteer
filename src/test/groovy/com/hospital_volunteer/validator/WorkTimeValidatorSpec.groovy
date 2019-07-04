package com.hospital_volunteer.validator

import com.hospital_volunteer.dto.WorkTimeSaveDTO
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import javax.validation.ConstraintValidatorContext
import javax.validation.ValidationException

class WorkTimeValidatorSpec extends Specification {

    @Subject
    WorkTimeValidator workTimeValidator = new WorkTimeValidator()

    @Unroll
    def "Should validate object and return true"() {
        given:
        def constraintValidatorContext = Mock(ConstraintValidatorContext)
        def workTimeSaveDTO = new WorkTimeSaveDTO(name, year, month, day, startHour, numberOfHours)

        when:
        def valid = workTimeValidator.isValid(workTimeSaveDTO, constraintValidatorContext)

        then:
        noExceptionThrown()
        valid

        where:
        name     | year | month | day | startHour | numberOfHours
        "Leszek" | 2019 | 12    | 3   | 3         | 2
        "Leszek" | 2020 | 2     | 28  | 12        | 1
        "Leszek" | 2020 | 1     | 11  | 15        | 29
    }

    @Unroll
    def "Should fail to validation because incorrect dataTime"() {
        given:
        def constraintValidatorContext = Mock(ConstraintValidatorContext)
        def workTimeSaveDTO = new WorkTimeSaveDTO("Leszek", year, month, day, startHour, numberOfHours)

        when:
        def valid = workTimeValidator.isValid(workTimeSaveDTO, constraintValidatorContext)

        then:
        noExceptionThrown()
        !valid

        where:
        year | month | day | startHour | numberOfHours
        2010 | 12    | 1   | 2         | 10     //incorrect date - past
        2019 | 14    | 15  | 1         | 2      //incorrect month
        2019 | 12    | 32  | 1         | 2      //incorrect day of month
        2020 | 12    | 11  | 26        | 5      //incorrect hour of work start
        2020 | 2     | 31  | 2         | 3      //incorrect date 31.02
        2020 | 2     | 5   | 2         | 0      //incorrect number of hours
    }

    def "Should throw ValidationException"() {
        given:
        def constraintValidatorContext = Mock(ConstraintValidatorContext)

        when:
        workTimeValidator.isValid("Hello", constraintValidatorContext)

        then:
        def thrown = thrown(ValidationException)
        thrown.message == "Validation not supported"
    }
}

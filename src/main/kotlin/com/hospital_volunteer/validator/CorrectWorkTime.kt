package com.hospital_volunteer.validator

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [WorkTimeValidator::class])
annotation class CorrectWorkTime(val message: String = "Incorrect working time",
                                 val groups: Array<KClass<*>> = [],
                                 val payload: Array<KClass<out Payload>> = [])

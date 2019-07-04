package com.hospital_volunteer.dto

import com.hospital_volunteer.validator.CorrectWorkTime
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor
import javax.validation.constraints.*

@CorrectWorkTime
@Builder
@NoArgsConstructor
@AllArgsConstructor
data class WorkTimeSaveDTO(

        @NotBlank
        val volunteerName: String,
        val year: Int,
        val month: Int,
        val day: Int,
        val startHour: Int,
        val numberOfHours: Int
)
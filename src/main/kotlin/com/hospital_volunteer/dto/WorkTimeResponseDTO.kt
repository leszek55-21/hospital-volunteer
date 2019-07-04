package com.hospital_volunteer.dto

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import java.time.LocalDateTime

@NoArgsConstructor
@AllArgsConstructor
data class WorkTimeResponseDTO(
        var volunteerName: String? = null,
        var startDate: LocalDateTime? = null,
        var endDate: LocalDateTime? = null,
        var startHour: Int? = null,
        var numberOfHours: Int? = null)

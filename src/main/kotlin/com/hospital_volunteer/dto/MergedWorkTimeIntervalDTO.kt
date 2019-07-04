package com.hospital_volunteer.dto

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import java.time.LocalDateTime

@AllArgsConstructor
@NoArgsConstructor
data class MergedWorkTimeIntervalDTO(
        var startDate: LocalDateTime? = null,
        var endDate: LocalDateTime? = null
)
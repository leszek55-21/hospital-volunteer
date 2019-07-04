package com.hospital_volunteer.entity

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "workTime")
@NoArgsConstructor
@AllArgsConstructor
data class WorkTime (

        var volunteerName: String? = null,

        var startDate: LocalDateTime,
        var endDate: LocalDateTime,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L,

        var startingHour: Int? = null,
        var numberOfHours: Int? = null)

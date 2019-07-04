package com.hospital_volunteer.repository

import com.hospital_volunteer.entity.WorkTime
import org.springframework.data.repository.CrudRepository

import java.util.Optional

interface WorkTimeRepository : CrudRepository<WorkTime, String> {
    override fun findById(id: String): Optional<WorkTime>
}

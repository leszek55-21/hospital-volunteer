package com.hospital_volunteer.mapper

import com.hospital_volunteer.dto.WorkTimeResponseDTO
import com.hospital_volunteer.entity.WorkTime

object WorkTimeResponseMapper {
    fun toWorkTimeResponse(workTime: WorkTime): WorkTimeResponseDTO {
        val workTimeResponseDTO  = WorkTimeResponseDTO()
        workTimeResponseDTO.volunteerName = workTime.volunteerName
        workTimeResponseDTO.startDate = workTime.startDate
        workTimeResponseDTO.endDate = workTime.endDate
        workTimeResponseDTO.startHour = workTime.startingHour
        workTimeResponseDTO.numberOfHours = workTime.numberOfHours
        return workTimeResponseDTO
    }
}
package com.hospital_volunteer.mapper

import com.hospital_volunteer.dto.MergedWorkTimeIntervalDTO
import com.hospital_volunteer.entity.WorkTime

object MergedWorkTimeIntervalMapper {
    fun toMergedWorkTimeDTO(workTime: WorkTime): MergedWorkTimeIntervalDTO {
        val mergedWorkTimeIntervalDTO = MergedWorkTimeIntervalDTO()
        mergedWorkTimeIntervalDTO.startDate = workTime.startDate
        mergedWorkTimeIntervalDTO.endDate = workTime.endDate
        return mergedWorkTimeIntervalDTO
    }
}
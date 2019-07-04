package com.hospital_volunteer.controller

import com.hospital_volunteer.dto.MergedWorkTimeIntervalDTO
import com.hospital_volunteer.dto.WorkTimeSaveDTO
import com.hospital_volunteer.dto.WorkTimeResponseDTO
import com.hospital_volunteer.entity.WorkTime
import com.hospital_volunteer.service.WorkTimeService
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import lombok.AllArgsConstructor
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Slf4j
@RestController
@RequiredArgsConstructor
class WorkTimeController (val workTimeService: WorkTimeService) {

    @PostMapping("/")
    fun save(@Valid @RequestBody saveWorkTimeDTO: WorkTimeSaveDTO): WorkTimeResponseDTO {
        return workTimeService.save(saveWorkTimeDTO)
    }

    @GetMapping("/merged")
    fun getMergedIntervals(): Iterable<MergedWorkTimeIntervalDTO> {
        return workTimeService.getMergedIntervals()
    }

    @GetMapping("/overlapped")
    fun getOverlappedIntervalsNumber(): Int {
        return workTimeService.getOverlappedIntervalsCount()
    }

    @GetMapping("/")
    fun getAll(): Iterable<WorkTime> {
        log.println("getting all")
        return workTimeService.getAll()
    }
}
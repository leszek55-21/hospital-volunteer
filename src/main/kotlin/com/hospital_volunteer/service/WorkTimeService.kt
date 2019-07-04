package com.hospital_volunteer.service

import com.hospital_volunteer.dto.MergedWorkTimeIntervalDTO
import com.hospital_volunteer.dto.WorkTimeSaveDTO
import com.hospital_volunteer.dto.WorkTimeResponseDTO
import com.hospital_volunteer.entity.WorkTime
import com.hospital_volunteer.mapper.MergedWorkTimeIntervalMapper
import com.hospital_volunteer.mapper.WorkTimeResponseMapper
import com.hospital_volunteer.repository.WorkTimeRepository
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Slf4j
@Service
@RequiredArgsConstructor
class WorkTimeService(val workTimeRepository: WorkTimeRepository) {

    fun save(workTimeSaveDTO: WorkTimeSaveDTO): WorkTimeResponseDTO {
        log.println("saving $workTimeSaveDTO")
        val startDate = LocalDateTime.of(workTimeSaveDTO.year, workTimeSaveDTO.month, workTimeSaveDTO.day,
                workTimeSaveDTO.startHour, 0)
        val endDate = startDate.plusHours(workTimeSaveDTO.numberOfHours.toLong())
        val workTime = WorkTime(workTimeSaveDTO.volunteerName, startDate, endDate)
        workTime.startingHour = workTimeSaveDTO.startHour
        workTime.numberOfHours = workTimeSaveDTO.numberOfHours
        val savedWorkTime = workTimeRepository.save(workTime)

        return WorkTimeResponseMapper.toWorkTimeResponse(savedWorkTime)
    }

    fun getMergedIntervals(): MutableList<MergedWorkTimeIntervalDTO> {
        val sortedByDate = workTimeRepository.findAll().sortedBy { it.startDate }
                .sortedBy { it.startingHour }
        val mergedIntervals: MutableList<WorkTime> = prepareMergedIntervals(sortedByDate)
        log.println("Merged intervals: $mergedIntervals")
        val mergedWorkTimeIntervalDTOs: MutableList<MergedWorkTimeIntervalDTO> = mutableListOf()
        mergedIntervals.forEach { mergedWorkTimeIntervalDTOs.add(MergedWorkTimeIntervalMapper.toMergedWorkTimeDTO(it)) }
        return mergedWorkTimeIntervalDTOs
    }

    private fun prepareMergedIntervals(sortedByDate: List<WorkTime>): MutableList<WorkTime> {
        val mergedIntervals: MutableList<WorkTime> = mutableListOf()
        sortedByDate.forEach {
            mergeIntervals(mergedIntervals, it)
        }
        return mergedIntervals
    }

    private fun mergeIntervals(mergedIntervals: MutableList<WorkTime>, it: WorkTime) {
        if (mergedIntervals.isEmpty()) mergedIntervals.add(it)
        val lastInterval = mergedIntervals[mergedIntervals.size - 1]
        if (!qualifiesForMerge(it, lastInterval)) {
            mergedIntervals.add(it)
        } else if (qualifiesForMerge(it, lastInterval)) {
            lastInterval.endDate = it.endDate
        }
    }

    private fun qualifiesForMerge(qualifier: WorkTime, item: WorkTime) =
            qualifier.startDate.isBefore(item.endDate) || qualifier.startDate.isEqual(item.endDate)
                    && qualifier.endDate.isAfter(item.endDate)

    fun getOverlappedIntervalsCount(): Int {
        val sortedByDate = workTimeRepository.findAll().sortedBy { it.startDate }
                .sortedBy { it.startingHour }
        return countOverlappedIntervals(sortedByDate)
    }

    private fun countOverlappedIntervals(sortedByDate: List<WorkTime>): Int {
        val intervals: MutableList<Int> = mutableListOf()
        searchForOverlappedIntervals(sortedByDate, intervals)
        log.println("intervals: $intervals")
        return countOverlaps(intervals)
    }

    private fun searchForOverlappedIntervals(sortedByDate: List<WorkTime>, intervals: MutableList<Int>) {
        val startDate = LocalDateTime.of(1, 1, 1, 0, 0)
        val endDate = startDate.plusMinutes(1.toLong())
        var itemToCompare = WorkTime("Leszek", startDate, endDate, 1L, 6, 2)
        sortedByDate.forEach {
            if (overlaps(it, itemToCompare)) {
                intervals.add(1)
                if (it.endDate.isAfter(itemToCompare.endDate)) {
                    itemToCompare.endDate = it.endDate
                }
            } else {
                intervals.add(0)
                itemToCompare = it
            }
        }
    }

    private fun countOverlaps(intervals: MutableList<Int>): Int {
        var counter = 0
        for (i in 0..intervals.size - 2) {
            if (intervals[i] > intervals[i + 1]) {
                counter++
            }
        }
        if (intervals.isNotEmpty() && intervals[intervals.size - 1] == 1) {
            counter++
        }
        return counter
    }

    private fun overlaps(qualifier: WorkTime, item: WorkTime): Boolean {
        return qualifier.startDate.isEqual(item.startDate) || qualifier.startDate.isBefore(item.endDate)
    }

    fun getAll(): List<WorkTime> {
        val sortedByDate = workTimeRepository.findAll().sortedBy { it.startDate }
                .sortedBy { it.startingHour }
        return sortedByDate
    }
}
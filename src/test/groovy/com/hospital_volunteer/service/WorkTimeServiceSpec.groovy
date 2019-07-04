package com.hospital_volunteer.service

import com.hospital_volunteer.dto.MergedWorkTimeIntervalDTO
import com.hospital_volunteer.dto.WorkTimeResponseDTO
import com.hospital_volunteer.dto.WorkTimeSaveDTO
import com.hospital_volunteer.entity.WorkTime
import com.hospital_volunteer.repository.WorkTimeRepository
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDateTime

class WorkTimeServiceSpec extends Specification {

    def workTimeRepository = Mock(WorkTimeRepository)

    @Subject
    WorkTimeService workTimeService = new WorkTimeService(workTimeRepository)

    @Unroll
    def "Should save single workTime entity"() {
        given:
        def workTimeSaveDTO = new WorkTimeSaveDTO(name, year, month, day, startHour, numberOfHours)
        def startDate = LocalDateTime.of(year, month, day, startHour, 0)
        def endDate = startDate.plusHours(numberOfHours.toLong())

        1 * workTimeRepository.save(_ as WorkTime) >> new WorkTime(name, startDate, endDate, 1L, startHour, numberOfHours)
        when:
        def saved = workTimeService.save(workTimeSaveDTO)

        then:
        noExceptionThrown()
        saved.volunteerName == name
        saved.numberOfHours == numberOfHours
        saved.startDate == startDate
        saved.endDate == endDate
        saved.startHour == startHour

        where:
        name     | year | month | day | startHour | numberOfHours
        "Leszek" | 2019 | 12    | 3   | 3         | 2
        "Leszek" | 2020 | 2     | 28  | 12        | 1
        "Leszek" | 2020 | 1     | 11  | 15        | 29
    }

    //doesn't work properly
    def "Should get merged intervals"() {
        given:

        def startDate1 = LocalDateTime.of(2020, 6, 6, 6, 0)
        def endDate1 = startDate1.plusHours(2.toLong())
        def workTime1 = new WorkTime("Leszek", startDate1, endDate1, 1L, null, null)

        def startDate2 = LocalDateTime.of(2020, 6, 6, 6, 0)
        def endDate2 = startDate2.plusHours(3.toLong())
        def workTime2 = new WorkTime("Leszek", startDate2, endDate2, 1L, null, null)

        def startDate3 = LocalDateTime.of(2020, 6, 6, 8, 0)
        def endDate3 = startDate2.plusHours(2.toLong())
        def workTime3 = new WorkTime("Leszek", startDate3, endDate3, 1L, null, null)

        def startDate4 = LocalDateTime.of(2020, 6, 8, 8, 0)
        def endDate4 = startDate2.plusHours(5.toLong())
        def workTime4 = new WorkTime("Leszek", startDate4, endDate4, 1L, null, null)

        def list = [workTime1, workTime2, workTime3, workTime4]

        1 * workTimeRepository.findAll() >> list

        when:
        def mergedIntervals = workTimeService.getMergedIntervals()
        println(mergedIntervals)

        then:
        noExceptionThrown()
        mergedIntervals.size() == 2
        def resultStartDate1 = LocalDateTime.of(2020, 6, 6, 6, 0)
        mergedIntervals.get(0).startDate == resultStartDate1
        mergedIntervals.get(0).endDate == resultStartDate1.plusHours(4)

        def resultStartDate2 = LocalDateTime.of(2020, 6, 8, 8, 0)
        mergedIntervals.get(1).startDate == resultStartDate2
        mergedIntervals.get(1).endDate == resultStartDate2.plusHours(5)
    }

    def "Should return number of all overlapped work intervals"() {
        given:

        def startDate1 = LocalDateTime.of(2020, 6, 6, 6, 0)
        def endDate1 = startDate1.plusHours(2.toLong())
        def workTime1 = new WorkTime("Leszek", startDate1, endDate1, 1L, null, null)

        def startDate2 = LocalDateTime.of(2020, 6, 6, 6, 0)
        def endDate2 = startDate2.plusHours(3.toLong())
        def workTime2 = new WorkTime("Leszek", startDate2, endDate2, 1L, null, null)

        def startDate3 = LocalDateTime.of(2020, 6, 6, 8, 0)
        def endDate3 = startDate2.plusHours(2.toLong())
        def workTime3 = new WorkTime("Leszek", startDate3, endDate3, 1L, null, null)

        def startDate4 = LocalDateTime.of(2020, 6, 8, 8, 0)
        def endDate4 = startDate2.plusHours(5.toLong())
        def workTime4 = new WorkTime("Leszek", startDate4, endDate4, 1L, null, null)

        def list = [workTime1, workTime2, workTime3, workTime4]

        1 * workTimeRepository.findAll() >> list

        when:
        def overlappedIntervalsCount = workTimeService.getOverlappedIntervalsCount()

        then:
        noExceptionThrown()
        overlappedIntervalsCount == 1
    }

}

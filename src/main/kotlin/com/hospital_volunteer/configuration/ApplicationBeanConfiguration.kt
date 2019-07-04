package com.hospital_volunteer.configuration

import com.hospital_volunteer.entity.WorkTime
import com.hospital_volunteer.repository.WorkTimeRepository
import lombok.RequiredArgsConstructor
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

@Configuration
@RequiredArgsConstructor
class ApplicationBeanConfiguration {

    @Bean
    fun init(repository: WorkTimeRepository) = CommandLineRunner {
        println("Init")
        repository.save(WorkTime("Leszek", LocalDateTime.of(2019, 8, 20, 5, 0),
                LocalDateTime.of(2019, 8, 20, 7, 0)))
        repository.save(WorkTime("Jan", LocalDateTime.of(2019, 8, 20, 6, 0),
                LocalDateTime.of(2019, 8, 20, 8, 0)))
        repository.save(WorkTime("Sebastian", LocalDateTime.of(2019, 8, 20, 10, 0),
                LocalDateTime.of(2019, 8, 20, 12, 0)))
        repository.save(WorkTime("Bach", LocalDateTime.of(2019, 8, 20, 11, 0),
                LocalDateTime.of(2019, 8, 20, 14, 0)))
        repository.save(WorkTime("Wolfgang", LocalDateTime.of(2019, 8, 20, 20, 0),
                LocalDateTime.of(2019, 8, 21, 2, 0)))
        repository.save(WorkTime("Amadeusz", LocalDateTime.of(2019, 8, 25, 5, 0),
                LocalDateTime.of(2019, 8, 25, 7, 0)))
        repository.save(WorkTime("Mozzart", LocalDateTime.of(2019, 8, 25, 5, 0),
                LocalDateTime.of(2019, 8, 25, 7, 0)))
        repository.save(WorkTime("Jacek", LocalDateTime.of(2019, 8, 25, 7, 0),
                LocalDateTime.of(2019, 8, 25, 9, 0)))
        repository.save(WorkTime("Michał", LocalDateTime.of(2019, 8, 25, 9, 0),
                LocalDateTime.of(2019, 8, 25, 10, 0)))
        repository.save(WorkTime("Krzysztof", LocalDateTime.of(2019, 8, 25, 11, 0),
                LocalDateTime.of(2019, 8, 25, 18, 0)))

    }

}
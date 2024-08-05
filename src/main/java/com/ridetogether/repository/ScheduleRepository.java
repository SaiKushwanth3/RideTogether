package com.ridetogether.repository;

import com.ridetogether.model.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    List<Schedule> findByScheduleDate(LocalDate date);
    List<Schedule> findByScheduleDateBetween(LocalDateTime start, LocalDateTime end);
}

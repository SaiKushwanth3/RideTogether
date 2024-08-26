package com.ridetogether.repository;

import com.ridetogether.model.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {
  List<Schedule> findByScheduleDateBetween(LocalDateTime start, LocalDateTime end);

  List<Schedule> findByDriverId(String driverId);
}

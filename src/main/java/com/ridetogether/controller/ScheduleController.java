package com.ridetogether.controller;

import com.ridetogether.dto.ScheduleDTO;
import com.ridetogether.dto.ScheduleResponse;
import com.ridetogether.dto.SearchDTO;
import com.ridetogether.model.Schedule;
import com.ridetogether.service.ScheduleServiceImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

  @Autowired private ScheduleServiceImpl scheduleService;

  @GetMapping
  public List<ScheduleResponse> getSchedules(
      @RequestParam String source,
      @RequestParam String destination,
      @RequestParam int numberOfPassengers,
      @RequestParam LocalDate scheduleDate) {
    SearchDTO searchDTO =
        SearchDTO.builder()
            .source(source)
            .destination(destination)
            .numberOfPassengers(numberOfPassengers)
            .scheduleDate(scheduleDate)
            .build();
    return scheduleService.getSchedules(searchDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Schedule> getScheduleById(@PathVariable("id") String scheduleId) {
    Schedule schedule = scheduleService.getScheduleById(scheduleId);
    if (Objects.nonNull(schedule)) return ResponseEntity.ok(schedule);
    return ResponseEntity.badRequest().build();
  }

  @PostMapping
  public ResponseEntity<Schedule> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(scheduleService.createSchedule(scheduleDTO));
  }

  @PutMapping("/{id}")
  public void updateSchedule(@RequestBody ScheduleDTO scheduleDTO) {}

  @GetMapping("/hi")
  public List<Schedule> getAllSchedule() {
    return scheduleService.getSchedules();
  }
}

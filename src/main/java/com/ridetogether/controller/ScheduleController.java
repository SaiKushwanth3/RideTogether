package com.ridetogether.controller;

import com.ridetogether.dto.ScheduleDTO;
import com.ridetogether.dto.SearchDTO;
import com.ridetogether.model.Schedule;
import com.ridetogether.service.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired private ScheduleServiceImpl scheduleService;

    @GetMapping
    public List<Schedule> getSchedules(@RequestBody SearchDTO searchDTO) {
        return scheduleService.getSchedules(searchDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(String scheduleId) {
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        if(Objects.nonNull(schedule)) return ResponseEntity.ok(schedule);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(scheduleDTO));
    }

    @PutMapping("/{id}")
    public void updateSchedule(@RequestBody ScheduleDTO scheduleDTO) {

    }
}

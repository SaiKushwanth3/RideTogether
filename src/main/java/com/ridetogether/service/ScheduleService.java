package com.ridetogether.service;

import com.ridetogether.dto.ScheduleDTO;
import com.ridetogether.dto.ScheduleResponse;
import com.ridetogether.dto.SearchDTO;
import com.ridetogether.model.Schedule;
import java.util.List;

public interface ScheduleService {
  public List<ScheduleResponse> getSchedules(SearchDTO searchDTO);

  public Schedule getScheduleById(String scheduleId);

  public List<Schedule> getScheduleByDriverId(String driverId);

  public Schedule updateSchedule(ScheduleDTO scheduleDTO);

  public List<Schedule> getAllSchedules();

  public Schedule createSchedule(ScheduleDTO scheduleDTO);
}

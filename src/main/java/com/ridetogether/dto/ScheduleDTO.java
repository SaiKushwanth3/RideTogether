package com.ridetogether.dto;

import com.ridetogether.model.Route;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduleDTO {

  private Route route;
  private String vehicleId;
  private LocalDateTime scheduleDate;
}

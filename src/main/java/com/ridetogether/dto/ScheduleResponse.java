package com.ridetogether.dto;

import com.ridetogether.model.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ScheduleResponse {
  private String scheduleId;
  private Route route;
  private Stop source;
  private Stop destination;
  private Vehicle vehicle;
  private LocalDateTime scheduleDate;
  private ScheduleStatus status;
  private List<Seat> seats;
  private int availableSeats;
}

package com.ridetogether.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "Schedule")
public class Schedule {

  @Id private String scheduleId;
  private Route route;
  private Vehicle vehicle;
  private String driverId;
  private LocalDateTime scheduleDate;
  private ScheduleStatus status;
  private List<Seat> seats;
}

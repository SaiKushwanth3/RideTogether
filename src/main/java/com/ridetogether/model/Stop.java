package com.ridetogether.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Stop {
  private String stopId;
  private String stopName;
  private BigDecimal distanceFromSource;
  private LocalDateTime arrivalTime;
  private LocalDateTime departureTime;
}

package com.ridetogether.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Segment {
  private String sourceId;
  private String destinationId;
  private String userId;
  private Passenger passenger;
  private SeatStatus status;
}

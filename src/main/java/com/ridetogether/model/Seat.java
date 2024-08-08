package com.ridetogether.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Seat {
  private String seatId;
  private SeatStatus status;
  private List<Segment> segments;
}

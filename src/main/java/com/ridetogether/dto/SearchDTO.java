package com.ridetogether.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchDTO {

  private String source;
  private String destination;
  private int numberOfPassengers;
  private LocalDate scheduleDate;
}

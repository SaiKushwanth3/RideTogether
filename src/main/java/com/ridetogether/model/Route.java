package com.ridetogether.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Route {

  private String routeId;
  private String source;
  private String destination;
  private List<Stop> stops;
}

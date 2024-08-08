package com.ridetogether.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
  private String vehicleId;
  private String driverId;
  private int noOfSeats;
  private String model;
  private String vehicleName;
}

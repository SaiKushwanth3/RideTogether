package com.ridetogether.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Passenger {
  private String name;
  private Integer age;
  private String gender;
}

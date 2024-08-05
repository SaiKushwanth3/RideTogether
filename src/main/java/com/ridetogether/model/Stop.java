package com.ridetogether.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Stop {
    private String stopId;
    private String stopName;
    private BigDecimal distanceFromSource;
}

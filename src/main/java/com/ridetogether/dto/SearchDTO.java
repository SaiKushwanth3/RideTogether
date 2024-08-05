package com.ridetogether.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchDTO {

    private String source;
    private String destination;
    private int numberOfPassengers;
    private LocalDate scheduleDate;
}

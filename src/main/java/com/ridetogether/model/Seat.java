package com.ridetogether.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Seat {
    private String seatId;
    private String passengerId;
    private List<Integer> seatStatuses;
}

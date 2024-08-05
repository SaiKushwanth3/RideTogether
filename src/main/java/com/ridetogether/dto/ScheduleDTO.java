package com.ridetogether.dto;

import com.ridetogether.model.Route;
import com.ridetogether.model.ScheduleStatus;
import com.ridetogether.model.Seat;
import com.ridetogether.model.Timing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduleDTO {

    private Route route;
    private String vehicleId;
    private String driverId;
    private LocalDate scheduleDate;
    private List<Timing> timings;
    private List<Seat> seats;
}

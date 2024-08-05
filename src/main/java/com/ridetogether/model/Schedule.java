package com.ridetogether.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Schedule")
public class Schedule {

    @Id
    private String id;
    private Route route;
    private String vehicleId;
    private String driverId;
    private LocalDateTime scheduleDate;
    private List<Timing> timings;
    private ScheduleStatus status;
    private List<Seat> seats;
}

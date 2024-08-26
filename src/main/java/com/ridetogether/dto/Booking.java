package com.ridetogether.dto;

import com.ridetogether.model.Seat;
import com.ridetogether.model.Stop;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
  private Stop source;
  private Stop destination;
  private String scheduleId;
  private String vehicleId;
  private LocalDateTime travelDate;
  private LocalDateTime bookingDate;
  private String userId;
  private List<Seat> bookedSeats;
  private BigDecimal amount;
}

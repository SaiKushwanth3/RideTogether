package com.ridetogether.service;

import com.ridetogether.dto.ScheduleDTO;
import com.ridetogether.dto.ScheduleResponse;
import com.ridetogether.dto.SearchDTO;
import com.ridetogether.model.*;
import com.ridetogether.repository.ScheduleRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ScheduleServiceImpl implements ScheduleService {

  @Autowired private ScheduleRepository scheduleRepository;
  @Autowired private RestTemplate restTemplate;

  @Override
  public List<ScheduleResponse> getSchedules(SearchDTO searchDTO) {
    LocalDateTime startOfDay = searchDTO.getScheduleDate().atStartOfDay();
    LocalDateTime endOfDay = searchDTO.getScheduleDate().atTime(LocalTime.MAX);
    List<ScheduleResponse> scheduleResponses = new ArrayList<>();
    scheduleRepository
        .findByScheduleDateBetween(startOfDay, endOfDay)
        .forEach(
            schedule ->
                getSchedulesFromSourceToDestination(schedule, searchDTO, scheduleResponses));
    return scheduleResponses;
  }

  @Override
  public Schedule getScheduleById(String scheduleId) {
    return scheduleRepository.findById(scheduleId).orElse(null);
  }

  @Override
  public Schedule updateSchedule(ScheduleDTO scheduleDTO) {
    return null;
  }

  @Override
  public Schedule createSchedule(ScheduleDTO scheduleDTO) {

    Schedule schedule =
        Schedule.builder()
            .route(scheduleDTO.getRoute())
            .status(ScheduleStatus.SCHEDULED)
            .scheduleDate(scheduleDTO.getScheduleDate())
            .build();
    // fetch from DriverService
    Vehicle vehicle =
        restTemplate.getForObject(
            "http://localhost:8081/vehicle/{vehicleId}", Vehicle.class, scheduleDTO.getVehicleId());
    if (Objects.isNull(vehicle)) return null;

    schedule.setVehicle(vehicle);
    List<Stop> stops = schedule.getRoute().getStops();
    List<Seat> seats = new ArrayList<>();
    List<Segment> segments = new ArrayList<>();
    /*for(int i=0;i<=stops.size()-1;i++){
      Segment segment = Segment.builder().sourceId(stops.get(i).getStopName()).destinationId(stops.get(i+1).getStopName()).build();
      segments.add(segment);
    }*/

    for (int i = 1; i <= vehicle.getNoOfSeats(); i++) {
      Seat seat = new Seat();
      seat.setSeatId("seat" + i);
      seat.setSegments(segments);
      seats.add(seat);
    }
    schedule.setSeats(seats);
    return scheduleRepository.save(schedule);
  }

  public List<Schedule> getSchedules() {
    return scheduleRepository.findAll();
  }

  private void getSchedulesFromSourceToDestination(
      Schedule schedule, SearchDTO searchDTO, List<ScheduleResponse> scheduleResponses) {
    List<Stop> stops = schedule.getRoute().getStops();
    Stop source =
        stops.stream()
            .filter(stop -> searchDTO.getSource().equals(stop.getStopName()))
            .findFirst()
            .orElse(null);
    Stop destination =
        stops.stream()
            .filter(stop -> searchDTO.getDestination().equals(stop.getStopName()))
            .findFirst()
            .orElse(null);
    if (Objects.isNull(source) || Objects.isNull(destination)) return;

    int sourceIndex = stops.indexOf(source);
    int destinationIndex = stops.indexOf(destination);
    if (sourceIndex < destinationIndex && schedule.getStatus().equals(ScheduleStatus.SCHEDULED)) {

      List<Seat> availableSeats =
          schedule.getSeats().stream()
              .map(
                  seat -> {
                    if (!checkSeatAvailability(
                        seat.getSegments(), source.getStopId(), destination.getStopId())) {
                      return Seat.builder()
                          .seatId(seat.getSeatId())
                          .status(SeatStatus.BOOKED)
                          .build();
                    }
                    return Seat.builder()
                        .seatId(seat.getSeatId())
                        .status(SeatStatus.AVAILABLE)
                        .build();
                  })
              .toList();

      // do the below step only if available seats are greater than or equal to seats required
      ScheduleResponse scheduleResponse =
          ScheduleResponse.builder()
              .scheduleId(schedule.getScheduleId())
              .source(source)
              .destination(destination)
              .vehicle(schedule.getVehicle())
              .scheduleDate(schedule.getScheduleDate())
              .status(schedule.getStatus())
              .route(schedule.getRoute())
              .seats(availableSeats)
              .build();
      scheduleResponses.add(scheduleResponse);
    }
  }

  private boolean checkSeatAvailability(
      List<Segment> segments, String sourceId, String destinationId) {

    return segments.stream()
        .allMatch(
            segment ->
                (sourceId.compareTo(segment.getSourceId()) < 0
                        && destinationId.compareTo(segment.getSourceId()) <= 0)
                    || (sourceId.compareTo(segment.getDestinationId()) >= 0
                        && destinationId.compareTo(segment.getDestinationId()) > 0));
  }
}

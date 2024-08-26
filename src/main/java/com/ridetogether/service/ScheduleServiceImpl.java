package com.ridetogether.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.ridetogether.dto.ScheduleDTO;
import com.ridetogether.dto.ScheduleResponse;
import com.ridetogether.dto.SearchDTO;
import com.ridetogether.model.*;
import com.ridetogether.repository.ScheduleRepository;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ScheduleServiceImpl implements ScheduleService {

  @Autowired private HoldingService holdingService;

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
            .scheduleId(generateId())
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
    List<Seat> seats = new ArrayList<>();
    for (int i = 1; i <= vehicle.getNoOfSeats(); i++) {
      Seat seat = new Seat();
      seat.setSeatId("seat_" + i);
      seat.setSegments(new ArrayList<>());
      seats.add(seat);
    }
    schedule.setSeats(seats);
    return scheduleRepository.save(schedule);
  }

  @Override
  public List<Schedule> getAllSchedules() {
    return scheduleRepository.findAll();
  }

  @Override
  public List<Schedule> getScheduleByDriverId(String driverId) {
    return scheduleRepository.findByDriverId(driverId);
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

      Set<String> heldSeats = holdingService.getAllHeldSeats();
      List<Seat> availableSeats =
          schedule.getSeats().stream()
              .filter(
                  seat ->
                      checkSeatAvailability(
                              seat.getSegments(), source.getStopId(), destination.getStopId())
                          && !heldSeats.contains(schedule.getScheduleId() + "_" + seat.getSeatId()))
              .map(
                  seat ->
                      Seat.builder().seatId(seat.getSeatId()).status(SeatStatus.AVAILABLE).build())
              .toList();

      // do the below step only if available seats are greater than or equal to seats required
      if (availableSeats.size() >= searchDTO.getNumberOfPassengers()) {
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
                .availableSeats(availableSeats.size())
                .build();
        scheduleResponses.add(scheduleResponse);
      }
    }
  }

  private boolean checkSeatAvailability(
      List<Segment> segments, String sourceId, String destinationId) {
    Integer srcId = getStopId(sourceId);
    Integer dstId = getStopId(destinationId);
    return segments.stream()
        .allMatch(
            segment ->
                (srcId < getStopId(segment.getSourceId())
                        && dstId <= getStopId(segment.getSourceId()))
                    || (srcId >= getStopId(segment.getDestinationId())
                        && dstId > getStopId(segment.getDestinationId())));
  }

  private Integer getStopId(String stopId) {

    return Integer.valueOf(stopId.substring(4));
  }

  private String generateId() {
    char[] numbers = "0123456789".toCharArray();
    SecureRandom random = new SecureRandom();
    ZoneId zoneId = ZoneId.of("Asia/Kolkata");
    ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
    LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
    return NanoIdUtils.randomNanoId(random, numbers, 4) + localDateTime.format(formatter);
  }
}

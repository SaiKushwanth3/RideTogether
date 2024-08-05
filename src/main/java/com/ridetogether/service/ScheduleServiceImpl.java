package com.ridetogether.service;

import com.ridetogether.dto.ScheduleDTO;
import com.ridetogether.dto.SearchDTO;
import com.ridetogether.model.Schedule;
import com.ridetogether.model.Stop;
import com.ridetogether.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired private ScheduleRepository scheduleRepository;

    @Override
    public List<Schedule> getSchedules(SearchDTO searchDTO) {
        LocalDateTime startOfDay = searchDTO.getScheduleDate().atStartOfDay();
        LocalDateTime endOfDay = searchDTO.getScheduleDate().atTime(LocalTime.MAX);

        return scheduleRepository.findByScheduleDateBetween(startOfDay, endOfDay)
                .stream()
                .filter(schedule -> getSchedulesFromSourceToDestination(schedule, searchDTO))
                .collect(Collectors.toList());
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
        return null;
    }

    private boolean getSchedulesFromSourceToDestination(Schedule schedule, SearchDTO searchDTO){
        List<Stop> stops = schedule.getRoute().getStops();
        Stop source = stops.stream().filter(stop -> searchDTO.getSource().equals(stop.getStopName())).findFirst().orElse(null);
        Stop destination = stops.stream().filter(stop -> searchDTO.getDestination().equals(stop.getStopName())).findFirst().orElse(null);
        if(Objects.isNull(source) && Objects.isNull(destination)) return false;


        int sourceIndex = stops.indexOf(source);
        int destinationIndex = stops.indexOf(destination);
        if(sourceIndex < destinationIndex){
            long availableSeats = schedule.getSeats().stream()
                    .map(seat ->
                            IntStream.rangeClosed(sourceIndex, destinationIndex)
                            .map(seat.getSeatStatuses()::get)
                            .sum())
                    .filter(seatSum -> seatSum<2).count();
            return availableSeats >= searchDTO.getNumberOfPassengers();
        }
        return false;
    }
}

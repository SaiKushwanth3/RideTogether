package com.ridetogether.service;

import com.ridetogether.model.Trip;
import com.ridetogether.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImpl implements TripService{
    @Autowired private TripRepository tripRepository;
    @Override
    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }
}

package com.ridetogether.controller;

import com.ridetogether.model.Trip;
import com.ridetogether.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripResource {

    @Autowired private TripService tripService;
    @PostMapping("/trip")
    public Trip createTrip(@RequestBody Trip trip) {
        return tripService.createTrip(trip);
    }
}

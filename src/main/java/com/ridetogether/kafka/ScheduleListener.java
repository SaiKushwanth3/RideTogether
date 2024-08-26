package com.ridetogether.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridetogether.dto.Booking;
import org.springframework.stereotype.Service;

@Service
public class ScheduleListener {
  //    @KafkaListener(topics = "booking",groupId = "group-1")
  public void consumeBooking(String bookingRecord) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    Booking booking = objectMapper.readValue(bookingRecord, Booking.class);
    System.out.println(booking);
  }
}

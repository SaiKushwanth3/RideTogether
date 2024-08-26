package com.ridetogether.service;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class HoldingService {

  @Autowired private StringRedisTemplate redisTemplate;

  public Set<String> getAllHeldSeats() {

    return redisTemplate.keys("*");
  }
}

package com.bancohipotecario.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bancohipotecario.dto.MotorBikePriceDTO;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate; 

    @Autowired
    private ObjectMapper objectMapper;

    private static final String REDIS_KEY = "averagePricesByBrand";

    public void saveAveragePrices( List<MotorBikePriceDTO> motoPriceList) {
        try {
        	long duration = 60; 
            TimeUnit timeUnit = TimeUnit.SECONDS;
            String json = objectMapper.writeValueAsString(motoPriceList);
            redisTemplate.opsForValue().set(REDIS_KEY, json, duration, timeUnit);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); 
        }
    }
    

    public List<MotorBikePriceDTO> getAveragePrices() {
        try {
            String json = redisTemplate.opsForValue().get(REDIS_KEY);
            
            if(json!=null) {
            return objectMapper.readValue(json, new TypeReference<List<MotorBikePriceDTO>>() {});
            }
            return null;
        } catch (JsonProcessingException e) {
            e.printStackTrace(); 
            return null;
        }
    }
}
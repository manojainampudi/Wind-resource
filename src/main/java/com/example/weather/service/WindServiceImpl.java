package com.example.weather.service;

import com.example.weather.Model.Wind;
import com.example.weather.config.EhCacheConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class WindServiceImpl implements WindService {

  private static final Logger logger = LoggerFactory.getLogger(EhCacheConfig.class);

  @Autowired
  private RestTemplate restTemplate;

  @Value("${api.key}")
  String apiKey;

  String weatherApiUrl = "http://api.openweathermap.org/data/2.5/weather?zip=";

  @Override
  @Cacheable(value = "windInfo", key = "#zipCode")
  public Wind getWindInfoByZipcode(String zipCode) throws IOException {

    logger.info("Retrieving from third party API for " + zipCode);

    String url = weatherApiUrl + zipCode + ",us&APPID=" + apiKey;

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
    logger.info(responseEntity.getBody());
    byte[] jsonData = responseEntity.getBody().getBytes();
    ObjectMapper obj = new ObjectMapper();
    JsonNode windNode = obj.readTree(jsonData).path("wind");
    logger.info(String.valueOf(windNode));
    Wind wind = obj.readValue(String.valueOf(windNode), Wind.class);
    return wind;
  }
}

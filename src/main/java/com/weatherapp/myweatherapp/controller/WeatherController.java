package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WeatherController {

  @Autowired
  WeatherService weatherService;

  @GetMapping("/forecast/{city}")
  public ResponseEntity<CityInfo> forecastByCity(@PathVariable("city") String city) {

    CityInfo ci = weatherService.forecastByCity(city);

    return ResponseEntity.ok(ci);
  }

  // TODO: using Visual Crossing Weather API given two city names, compare the length of the daylight hours and return the city with the longest day
  @GetMapping("/compare-daylight/{city1}/{city2}")
  public ResponseEntity<String> compareDayLight(@PathVariable("city1") String city1, @PathVariable("city2") String city2) {

    CityInfo daylightC1 = weatherService.forecastByCity(city1);
    CityInfo daylightC2 = weatherService.forecastByCity(city2);
    
    int daylightC1 = weatherService.compareDayLight(city1);
    int daylightC2 = weatherService.compareDayLight(city2);
    String result;

    if (daylightC1 > daylightC2) {
      result = city1;
    } else if (daylightC1 < daylightC2) {
      result = city2;
    } else {
      result = "Both cities have the same length of day";
    }

    return ResponseEntity.ok(result);
  }

  private String longestDay(String city) {
    CityInfo ci = weatherService.forecastByCity(city); 
    if (ci != null && ci.CurrentConditions() != null) {
      String sunriseStr = ci.CurrentConditions().sunrise;
      String sunsetStr = ci.CurrentConditions().sunset;

      // Convert/extract time from string retrieved in .sunrise and .sunset

      // Then find the difference between the two
      

    }
  }


  // TODO: using Visual Crossing Weather API given two city names, check which city its currently raining in
  @GetMapping("/check-rain/{city1}/{city2}")
  public ResponseEntity<String> checkRain(@PathVariable("city1") String city1, @PathVariable("city2") String city2) {
    
    CityInfo ci1 = weatherService.forecastByCity(city1);
    CityInfo ci2 = weatherService.forecastByCity(city2);

    boolean rainCity1 = isRaining(city1);
    boolean rainCity2 = isRaining(city2);

    String result;
    if(rainCity1 && rainCity1) {
      result = "Both cities are raining";
    } else if(rainCity1) {
      result = city1;
    } else if (rainCity2) {
      result = city2;
    } else {
      result = "Both cities are not raining";
    }
    // CityInfo.CurrentConditions currentConditionsc1 = ci1.currentConditions;
    // String currentConditions1 = ci1.currentConditions;
    // CityInfo.CurrentConditions currentConditionsc2 = ci2.currentConditions;
    // String currentConditions2 = ci2.currentConditions;
    

    return ResponseEntity.ok(result);
  }

  private boolean isRaining(String city) {
    CityInfo ci = weatherService.forecastByCity(city); 
    if (ci != null && ci.CurrentConditions() != null) {
      String condition = ci.CurrentConditions().currentConditions;
      return condition.toLowerCase().contains("rain");
    }
    return false;
  }

}
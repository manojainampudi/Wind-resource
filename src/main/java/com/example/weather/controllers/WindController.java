package com.example.weather.controllers;

import com.example.weather.Model.Wind;
import com.example.weather.exception.BadInputException;
import com.example.weather.service.WindService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class WindController {

    private WindService windService;

    public WindController (WindService windService){
      this.windService = windService;
    }


    @GetMapping("/wind/{zipCode}")
    public Wind getWeatherInfoByZipcode(@PathVariable String zipCode) throws IOException {

        if (zipCode != null && zipCode.length() == 5)
            return windService.getWindInfoByZipcode(zipCode);
        else
            throw new BadInputException("Zipcode entered is Invalid");
         }
}
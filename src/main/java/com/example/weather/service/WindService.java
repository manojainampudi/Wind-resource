package com.example.weather.service;

import com.example.weather.Model.Wind;

import java.io.IOException;

public interface WindService {

  Wind getWindInfoByZipcode(String zipCode) throws IOException;
}

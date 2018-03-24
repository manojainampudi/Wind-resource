package com.example.weather.controllers;



import com.example.weather.Model.Wind;
import com.example.weather.service.WindService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.CoreMatchers.is;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class WindControllerTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private WindService windService;

    @InjectMocks
    private WindController windController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRetrieveWeatherInfo() throws Exception {
        Wind wind = new Wind(6.5,120,16);
        mockMvc = MockMvcBuilders.standaloneSetup(windController).build();

        when(windService.getWindInfoByZipcode("44145")).thenReturn(wind);
        mockMvc.perform(get("/api/v1/wind/44145"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.speed", is(6.5)))
                .andExpect(jsonPath("$.deg", is(120)))
                .andExpect(jsonPath("$.gust", is(16)));

        verify(windService).getWindInfoByZipcode("44145");

    }
}

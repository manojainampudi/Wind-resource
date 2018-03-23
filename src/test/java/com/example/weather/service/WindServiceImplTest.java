package com.example.weather.service;

import com.example.weather.Model.Wind;
import com.example.weather.config.EhCacheConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Cache.class)
public class WindServiceImplTest {

    private static final String URL ="http://api.openweathermap.org/data/2.5/weather?zip=44145,us&APPID=null";

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    WindServiceImpl windService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cache cache;

    private CacheManager cacheManager;

    EhCacheConfig cacheConfig;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        cacheManager = PowerMockito.mock(CacheManager.class);
        cache = PowerMockito.mock(Cache.class);
        objectMapper = new ObjectMapper();
        cacheConfig = new EhCacheConfig("windInfoTest");
        EhCacheConfig.setCacheManager(cacheManager);

    }

    @Test
    public void getWindInfoByZipcode() throws IOException {

        Wind wind = new Wind(6.5,320,80);
        String windInfo = "{\"wind\":"+ objectMapper.writeValueAsString(wind)+"}";
        Wind result;
        when(cacheManager.getCache("windInfoTest")).thenReturn(cache);

        cache = cacheManager.getCache("windInfoTest");

        if(cache !=null && cache.get("44145") !=null)
           result = (Wind) cache.get("44145").getObjectValue();
        else {
            when(restTemplate.getForEntity(URL, String.class)).thenReturn(new ResponseEntity<>(windInfo, HttpStatus.OK));

             result = windService.getWindInfoByZipcode("44145");
            cacheConfig.cache("44145",result);
        }
        assertEquals(result.getDeg(),320);
        assertEquals(result.getGust(),80);
        verify(restTemplate, times(1)).getForEntity(URL,String.class);

    }
}
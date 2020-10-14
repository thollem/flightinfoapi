package com.artsgard.flightinfoapi.service;

import com.artsgard.flightinfoapi.serviceimpl.FlightInfoExternalServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class HttpConnectionServiceTest {

  @Spy
  FlightInfoExternalServiceImpl app;

  @Mock
  HttpURLConnection connection;
  
  //https://stackoverflow.com/questions/55070105/mockito-unit-test-with-httpurlconnection
  
  


  @Test
  void status() throws IOException {
    int expected = 200;
    doReturn(connection).when(app).getConnection(any(), any(), any());
    doReturn(expected).when(connection).getResponseCode();
    //URL url = new URL("https://flightxml.flightaware.com/json/FlightXML2/FlightInfoEx/findFlightInfoByTailnumber/EC-MYT");
    int status = connection.getResponseCode();
    

    Assertions.assertEquals(expected, status);
  }
}
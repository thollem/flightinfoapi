package com.artsgard.flightinfoapi.controller;

import com.artsgard.flightinfoapi.DTO.FlightInfo;
import com.artsgard.flightinfoapi.DTO.FlightInfoExResult;
import com.artsgard.flightinfoapi.mock.FlightInfoMock;
import com.artsgard.flightinfoapi.service.MapperService;
import com.artsgard.flightinfoapi.serviceimpl.FlightInfoExternalServiceImpl;
import com.artsgard.flightinfoapi.serviceimpl.FlightInfoServiceImpl;
import com.artsgard.flightinfoapi.serviceimpl.MapperServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author WillemDragstra
 */

@ExtendWith(MockitoExtension.class)
public class FlightControllerMockitoTest {

    private MockMvc mockMvc;

    @Mock
    private FlightInfoServiceImpl flightService;
    
    @Mock
    private FlightInfoExternalServiceImpl flightExtService;

    @InjectMocks
    FlightController flightController;

    private JacksonTester<FlightInfo> jsonFlight;
    private JacksonTester<FlightInfoExResult> jsonFlightResult;
    private JacksonTester<List<FlightInfo>> jsonFlights;
    private FlightInfo flight;
    private List<FlightInfo> flights;
    private FlightInfoExResult flightExResult;

    private final MapperService mapperService = new MapperServiceImpl();

    @BeforeEach
    public void setup() {
        
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();

        flight = FlightInfoMock.generateFlightInfo();
        flights = new ArrayList();
        FlightInfoMock.generateFlights().forEach((flg) -> {
            flights.add(flg);
        });
        flightExResult = FlightInfoMock.generateFlightInfoExResult();
    }
    
    @Test
    void testMockInjection() {
        assertThat(mockMvc).isNotNull();
        assertThat(jsonFlight).isNotNull();
        assertThat(jsonFlightResult).isNotNull();
        assertThat(jsonFlights).isNotNull();
        assertThat(flight).isNotNull();
        assertThat(flights).isNotNull();
        assertThat(flightExResult).isNotNull();
    }

    @Test
    public void testFindFlightInfoByTailnumber() throws Exception {
        given(flightExtService.getFlightInfo("EC-MYT", 0)).willReturn(flightExResult);

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/findFlightInfoByTailnumber/{tailnumber}", "EC-MYT"))
                .andExpect((content()
                .contentType(MediaType.APPLICATION_JSON_VALUE)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonFlightResult.write(flightExResult).getJson()
        );
    }

    @Test
    public void testFindFlightInfosByIdent() throws Exception {
        given(flightService.findFlightsInfoByIdent(any(String.class))).willReturn(flights);
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/findFlightInfosByIdent/{ident}", "some-ident"))
                .andExpect((content()
                .contentType(MediaType.APPLICATION_JSON_VALUE)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonFlights.write(flights).getJson()
        );
    }
}

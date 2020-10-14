package com.artsgard.flightinfoapi.controller;

import com.artsgard.flightinfoapi.DTO.FlightInfo;
import com.artsgard.flightinfoapi.DTO.FlightInfoExResult;
import com.artsgard.flightinfoapi.mock.FlightInfoMock;
import com.artsgard.flightinfoapi.service.MapperService;
import com.artsgard.flightinfoapi.serviceimpl.FlightInfoExternalServiceImpl;
import com.artsgard.flightinfoapi.serviceimpl.FlightInfoServiceImpl;
import com.artsgard.flightinfoapi.serviceimpl.MapperServiceImpl;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(FlightController.class)
public class FlightControllerSpringStandaloneContextTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @InjectMocks
    FlightController flightController;

    @MockBean
    private FlightInfoServiceImpl flightService;

    @MockBean
    private FlightInfoExternalServiceImpl flightExtService;

    @Autowired
    private JacksonTester<FlightInfo> jsonFlight;

    @Autowired
    private JacksonTester<List<FlightInfo>> jsonFlights;

    @Autowired
    private JacksonTester<FlightInfoExResult> jsonFlightResult;

    private FlightInfo flight;
    private List<FlightInfo> flights;
    private FlightInfoExResult flightExResult;

    private final MapperService mapperService = new MapperServiceImpl();

    @BeforeEach
    public void setup() {
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
        given(flightExtService.getFlightInfo("tail-number", 0)).willReturn(flightExResult);

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/findFlightInfoByTailnumber/{tailnumber}", "tail-number"))
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

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
import static org.mockito.BDDMockito.given;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author artsgard
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureJsonTesters
public class FlightControllerServerTest {

    @MockBean
    private FlightInfoServiceImpl flightService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
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
        assertThat(restTemplate).isNotNull();
        assertThat(jsonFlight).isNotNull();
        assertThat(jsonFlightResult).isNotNull();
        assertThat(jsonFlights).isNotNull();
        assertThat(flight).isNotNull();
        assertThat(flights).isNotNull();
        assertThat(flightExResult).isNotNull();
    }

    @Test
    public void testFindFlightInfoByTailnumber() throws Exception {
        given(flightExtService.getFlightInfo("tailnumber", 0)).willReturn(flightExResult);

        ResponseEntity<FlightInfoExResult> flightResponse = restTemplate.getForEntity("/findFlightInfoByTailnumber/tailnumber", FlightInfoExResult.class);

        assertThat(flightResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(flightResponse.getBody().equals(flightExResult));
    }

    @Test
    public void testFindFlightInfosByIdent() throws Exception {
        given(flightService.findFlightsInfoByIdent("ident")).willReturn(flights);

        ResponseEntity <FlightInfo[]> flightResponse = restTemplate.getForEntity("/findFlightInfosByIdent/ident", FlightInfo[].class);

        assertThat(flightResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(flightResponse.getBody().equals(flights));
    }

}

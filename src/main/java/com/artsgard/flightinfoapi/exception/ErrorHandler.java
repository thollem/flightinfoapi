/*
package com.artsgard.flightinfoapi.exception;

import com.artsgard.flightinfoapi.DTO.FlightxmlError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;


@Component
public class ErrorHandler implements ResponseErrorHandler {
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return ErrorUtil.isError(response.getStatusCode());
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        FlightxmlError error = objectMapper.readValue(response.getBody(), FlightxmlError.class);
        System.err.println(String.format("Error: %s", error.getError()));
    }
}
*/
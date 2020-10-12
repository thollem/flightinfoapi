package com.artsgard.flightinfoapi.exception;

import org.springframework.http.HttpStatus;

public class ErrorUtil {

    public static boolean isError(HttpStatus status) {
        HttpStatus.Series series = status.series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series) || HttpStatus.Series.SERVER_ERROR.equals(series));
    }
}

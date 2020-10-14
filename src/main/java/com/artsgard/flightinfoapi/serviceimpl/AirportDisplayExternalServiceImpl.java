package com.artsgard.flightinfoapi.serviceimpl;

import com.artsgard.flightinfoapi.DTO.AirportDisplay;
import com.artsgard.flightinfoapi.service.AirportDisplayExternalService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import org.springframework.beans.factory.annotation.Value;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author artsgard
 *
 */
@Service//("airportExtService")
public class AirportDisplayExternalServiceImpl implements AirportDisplayExternalService {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(AirportDisplayExternalServiceImpl.class);

    @Value("${auth.username}")
    private String authUsername;

    @Value("${auth.apikey}")
    private String authApikey;

    private final static String BASEURL = "http://flightxml.flightaware.com/json/FlightXML2/AirportInfo";
    private String auth;

    public AirportDisplayExternalServiceImpl() { }

    private AirportDisplay airportDisplay;

    @Override
    public AirportDisplay getAirportInfo(String airportCode) {
        auth = authUsername + ":" + authApikey;
        BufferedReader br = null;
        StringBuilder sb = null;
        airportDisplay = new AirportDisplay();
        
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(BASEURL)
                .queryParam("airportCode", airportCode);
        System.out.println("<<<<<<<<<<<<<<<<uriBuilder: " + uriBuilder.toUriString());
        
        HttpURLConnection con = null;
        try {
            con = getConnection(uriBuilder.toUriString(), "GET", "application/json; charset=UTF-8");

            InputStream str;

            int statusCode = con.getResponseCode();
            System.out.println("<<<<<<<<<<<<<<status: " + statusCode);

            if (statusCode >= 200 && statusCode < 400) {
                str = con.getInputStream();
            } else {
                //str = con.getErrorStream();
                String responseString = readErrorStream(con.getErrorStream());
                System.err.println("responce error string: " + responseString);
            }

            br = new BufferedReader(new InputStreamReader((con.getInputStream())));
            String output;
            sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            System.out.println("<<<<<<<<<<<<<<<<Server response: " + sb.toString());

            JSONObject result;
            try {
                result = new JSONObject(sb.toString());
                JSONObject airportDisplayJson = result.getJSONObject("AirportInfoResult");
                airportDisplay.setName(airportDisplayJson.getString("name"));
                airportDisplay.setLocation(airportDisplayJson.getString("location"));
                airportDisplay.setLongitude(airportDisplayJson.getDouble("longitude"));
                airportDisplay.setLatitude(airportDisplayJson.getDouble("latitude"));
                airportDisplay.setTimeZone(airportDisplayJson.getString("timezone"));

            } catch (JSONException ex) {
                Logger.getLogger(AirportDisplayExternalServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("<<<<<<<<<<<Airport Info Ex POJO Result: " + airportDisplay);
        } catch (IOException ex) {
            System.err.println("Server IOException: " + ex);
            logger.error("Server IOException: " + ex);
        } finally {
            con.disconnect();
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                System.err.println("Server IOException: " + ex);
                logger.error("Server IOException: " + ex);
            }
        }

        return airportDisplay;
    }

    private String readErrorStream(final InputStream inputStream) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuilder responseString = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            responseString.append(line);
        }
        bufferedReader.close();
        return responseString.toString();
    }

    private HttpURLConnection getConnection(String url, String requestMethod, String contentType) {
        try {
            URL serverAddress = new URL(url);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("some proxy here", 8080));
            HttpURLConnection con;

            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
            String authHeaderValue = "Basic " + new String(encodedAuth);

            try {
                con = (HttpURLConnection) serverAddress.openConnection(); //openConnection(proxy)
                con.setReadTimeout(15000);
                con.setConnectTimeout(15000);
                con.setRequestMethod(requestMethod);
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestProperty("Authorization", authHeaderValue);
                con.setRequestProperty("Content-Type", contentType); //"application/json; charset=UTF-8"
                con.setRequestProperty("Accept", "application/json");
                con.addRequestProperty("User-Agent", "Mozilla");
                con.connect();
            } catch (IOException ex) {
                System.err.println("Server IOException: " + ex);
                logger.error("Server IOException: " + ex);
                return null;
            }
            return con;
        } catch (MalformedURLException ex) {
            System.err.println("Server MalformedURLException: " + ex);
            logger.error("Server MalformedURLException: " + ex);
            return null;
        }
    }
}

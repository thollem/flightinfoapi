package com.artsgard.flightinfoapi.serviceimpl;

import com.artsgard.flightinfoapi.DTO.FlightInfo;
import com.artsgard.flightinfoapi.DTO.FlightInfoExResult;
import com.artsgard.flightinfoapi.service.FlightInfoExternalService;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author artsgard
 * EC-MYT
 */
@Service//("flightExtService")
public class FlightInfoExternalServiceImpl implements FlightInfoExternalService {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(FlightInfoExternalServiceImpl.class);

    @Value("${auth.username}")
    private String authUsername;

    @Value("${auth.apikey}")
    private String authApikey;

    private final static String BASEURL = "https://flightxml.flightaware.com/json/FlightXML2/FlightInfoEx";
    private String auth;

    public FlightInfoExternalServiceImpl() { }

    private FlightInfoExResult flightInfoExResult;
    private FlightInfo flightInfo;
    private List<FlightInfo> flightInfos;

    @Override
    public FlightInfoExResult getFlightInfo(String tailnumber, int offSet) {
        auth = authUsername + ":" + authApikey;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(BASEURL)
                .queryParam("ident", tailnumber)
                .queryParam("howMany", 15)
                .queryParam("offset", offSet);
        System.out.println("<<<<<<<<<<<<<<<<uriBuilder: " + uriBuilder.toUriString());
        
        BufferedReader br = null;
        StringBuilder sb = null;
        flightInfo = new FlightInfo();
        flightInfos = new ArrayList();
        flightInfoExResult = new FlightInfoExResult();
        HttpURLConnection con = null;
        
        try {
            con = getConnection(uriBuilder.toUriString(), "GET", "application/json; charset=UTF-8");
            InputStream str;

            int statusCode = con.getResponseCode();
            System.out.println("<<<<<<<<<<<<<<status flight info: " + statusCode);

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
            System.out.println("<<<<<<<<<<<<<<<<Server response flight info: " + sb.toString());

            JSONObject result;
            try {
                result = new JSONObject(sb.toString());
                JSONObject flightInfoJsonObject = result.getJSONObject("FlightInfoExResult");
                flightInfoExResult.setOffset(flightInfoJsonObject.getInt("next_offset"));
                //JSONObject offset = jsonObject.getJSONObject("next_offset");
                JSONArray jSONArrayFlights = flightInfoJsonObject.getJSONArray("flights");
                
                for (int i = 0; i < jSONArrayFlights.length(); ++i) {
                    JSONObject fl = jSONArrayFlights.getJSONObject(i);
                    FlightInfo flInfo = new FlightInfo();
                    flInfo.setFaFlightId(fl.getString("faFlightID"));
                    flInfo.setIdent(fl.getString("ident"));
                    flInfo.setAircraftType(fl.getString("aircrafttype"));
                    flInfo.setFiledEte(fl.getString("filed_ete"));
                    flInfo.setFiledTime(fl.getLong("filed_time"));
                    flInfo.setFiledDepartureTime(fl.getLong("filed_departuretime"));
                    flInfo.setFiledAirSpeedKts(fl.getInt("filed_airspeed_kts"));
                    flInfo.setFiledAirSpeedMach(fl.getString("filed_airspeed_mach"));
                    flInfo.setFiledAltitude(fl.getInt("filed_altitude"));
                    flInfo.setRoute(fl.getString("route"));
                    flInfo.setActualDepartureTime(fl.getLong("actualdeparturetime"));
                    flInfo.setEstimatedArrivalTime(fl.getLong("estimatedarrivaltime"));
                    flInfo.setActualArrivalTime(fl.getLong("actualarrivaltime"));
                    flInfo.setDiverted(fl.getString("diverted"));
                    flInfo.setOrigin(fl.getString("origin"));
                    flInfo.setDestination(fl.getString("destination"));
                    flInfo.setOriginCity(fl.getString("originCity"));
                    flInfo.setOriginName(fl.getString("originName"));
                    flInfo.setDestinationName(fl.getString("destinationName"));
                    flInfo.setDestinationCity(fl.getString("destinationCity"));
               
                    flightInfos.add(flInfo);
                }
            } catch (JSONException ex) {
                Logger.getLogger(FlightInfoExternalServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("<<<<<<<<<<<Flight Info Ex POJO Result: " + flightInfos);
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
        flightInfoExResult.setFlights(flightInfos);
        return flightInfoExResult;
    }

    private String readErrorStream(final InputStream inputStream) throws IOException {
        final StringBuilder responseString;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            responseString = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseString.append(line);
            }
        }
        return responseString.toString();
    }

    public HttpURLConnection getConnection(String url, String requestMethod, String contentType) {
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
                con.setRequestProperty("Content-Type", contentType);
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

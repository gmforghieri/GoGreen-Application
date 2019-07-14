package project.service;

import com.google.gson.Gson;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class TransportService {

    private String apiKey = "key=AIzaSyDBHAC7HNUYpf7pxC8qLWzimyUhSOmY7xY";

    /**
     * this method will parse a valid API URL for given parameters.
     *
     * @param apiUrl           the base URL of API to be used.
     * @param defaultParameter default parameter for this API such as scale, size, style .. etc.
     * @param locations        the locations to be inject in the API.
     * @param apiKey           if this is not found (null) use the default key
     * @return String for valid API URL ready to be send.
     */
    public String apiParser(String apiUrl, String defaultParameter,
                            String locations, String apiKey) {
        if (apiKey == null) {
            apiKey = this.apiKey;
        }
        return apiUrl + defaultParameter + "&"
                + locations.replaceAll(" ", "+") + "&" + apiKey;
    }

    /**
     * this method send a request to google API provided with two locations.
     * and parse the JSON response into DirectionInfo object
     *
     * @param locationFrom the origin place
     * @param locationTo   the destination place
     * @param travelMod    can be either "driving", "walking", "bicycling"
     *                     or "transit" (transit = public transport)
     * @return RouteInfo object which contains the distance and the time of the trip
     */
    public RouteInfo getRouteInfo(String locationFrom, String locationTo, String travelMod) {
        String apiUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?";
        String jsonResponse = "";

        try {
            if ((travelMod + locationFrom + locationTo).contains("null")) {
                throw new IOException();
            }
            String defaultParameter = "units=km" + "&"
                    + "mode=" + travelMod;
            String locations = "origins=" + locationFrom + "&" + "destinations=" + locationTo;

            URL url = new URL(apiParser(apiUrl, defaultParameter, locations, apiKey));
            System.out.println(url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            jsonResponse = response.toString();

        } catch (IOException e) {
            System.out.println("there is an issue with HTTP request for getting distance!");
            e.printStackTrace();
            return null;
        }

        Gson gson = new Gson();

        return gson.fromJson(jsonResponse, RouteInfo.class);
    }

    /**
     * this method calculate co2
     * emission corresponding to avrage cars in EU (130g CO2 per kilometer).
     * The calculation based on this resource:
     * https://ec.europa.eu/clima/policies/transport/vehicles/cars_en
     *
     * @param distance the distance travelled by car in meter
     * @return int represent co2 emission in kilogram
     */
    public double co2EmissionUsingCar(double distance) {
        return Math.floor((distance / 1000 * 0.13) * 100) / 100;
    }

    /**
     * this method calculate co2 emission
     * of average bus, train and tram for given distance (45g CO2 per kilometer).
     * The calculation based on this resource:
     * https://www.delijn.be/en/overdelijn/organisatie/zorgzaam-ondernemen/milieu/co2-uitstoot-voertuigen.html
     * https://www.delijn.be/en/overdelijn/organisatie/zorgzaam-ondernemen/milieu/co2-uitstoot-voertuigen.html
     *
     * @param distance the distance travelled by bus in meter
     * @return int represent co2 emission in kilogram
     */
    public double co2EmissionUsingPT(double distance) {
        return Math.floor((distance / 1000 * 0.045) * 100) / 100;
    }

    /**
     * This method parse a valid google map link
     * that gives the direction between two locations.
     *
     * @param locationFrom the origin place.
     * @param locationTo   the destination place.
     * @param travelMod    can be either "driving", "walking", "bicycling"
     *                     or "transit" (transit = public transport).
     * @return string of google map link ready to be open on browser.
     */
    public String getGoogleMapDirectionLink(
            String locationFrom, String locationTo, String travelMod) {
        String apiUrl = "https://www.google.com/maps/dir/?";
        String defaultParameter = "travelmode=" + travelMod;
        String locations = "origin=" + locationFrom + "&" + "destination=" + locationTo;

        return apiParser(apiUrl, defaultParameter, locations, "api=1");
    }

}

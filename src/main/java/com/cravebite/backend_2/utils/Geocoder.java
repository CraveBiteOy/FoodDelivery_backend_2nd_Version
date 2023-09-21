package com.cravebite.backend_2.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cravebite.backend_2.exception.CraveBiteGlobalExceptionHandler;
import com.cravebite.backend_2.models.request.OrderRequestDTO;
import com.cravebite.backend_2.models.request.RestaurantRequestDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Geocoder {
    @Value("${GEOCODING_RESOURCE}")
    private String GEOCODING_RESOURCE;

    @Value("${API_KEY}")
    private String API_KEY;

    public RestaurantRequestDTO geoEncode(RestaurantRequestDTO req) {
        return geoEncode(req, req.getAddress(), req.getZipcode(), req.getCity());
    }

    public OrderRequestDTO geoEncode(OrderRequestDTO req) {
        return geoEncode(req, req.getDeliveryAddress(), req.getDeliveryZipcode(), req.getDeliveryCity());
    }

    private <T> T geoEncode(T entity, String address, String zipcode, String city) {
        try {

            /**
             * creating instance of HttpClient
             * constructing the address
             * sending the req and getting the res
             */
            HttpClient httpClient = HttpClient.newHttpClient();
            String addressStr = address + "," + zipcode + " " + city + ", finland";
            String addressEncode = URLEncoder.encode(addressStr, "UTF-8");
            String uriRequest = GEOCODING_RESOURCE + "?key=" + API_KEY + "&address=" + addressEncode;

            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(uriRequest))
                    .timeout(Duration.ofMillis(2000)).build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String res = httpResponse.body();

            if (httpResponse.statusCode() != 200) {
                throw new CraveBiteGlobalExceptionHandler(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Geocoding API request failed with status code: " + httpResponse.statusCode() + ", response: "
                                + res);
            }

            /*
             * convert the res to jsonNode
             */
            ObjectMapper mapper = new ObjectMapper();
            JsonNode resJson = mapper.readTree(res);
            JsonNode results = resJson.get("results");

            if (results != null && results.isArray() && results.size() > 0) {
                /*
                 * get the first item and extract latitude and longitude
                 */
                JsonNode result = results.get(0);
                JsonNode geometry = result.get("geometry");
                JsonNode location = geometry != null ? geometry.get("location") : null;
                Double latitude = location != null && location.get("lat") != null ? location.get("lat").asDouble()
                        : null;
                Double longitude = location != null && location.get("lng") != null ? location.get("lng").asDouble()
                        : null;

                /*
                 * if not null, set the latitude and longitude
                 * on the entity based on the type.
                 */
                if (latitude != null && entity instanceof RestaurantRequestDTO) {
                    ((RestaurantRequestDTO) entity).setLatitude(latitude);
                }
                if (longitude != null && entity instanceof RestaurantRequestDTO) {
                    ((RestaurantRequestDTO) entity).setLongitude(longitude);
                }
                if (latitude != null && entity instanceof OrderRequestDTO) {
                    ((OrderRequestDTO) entity).setDestinationLatitude(latitude);
                }

                if (longitude != null && entity instanceof OrderRequestDTO) {
                    ((OrderRequestDTO) entity).setDestinationLongitude(longitude);
                }
            } else {
                throw new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND,
                        "Geocoding API returned no results. Response: " + res);
            }

        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error while geocoding. Exception: " + ex.getMessage());
        }

        return entity;
    }

}

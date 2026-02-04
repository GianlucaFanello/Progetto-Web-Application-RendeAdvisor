package it.unical.demacs.wa.rendeadvisor_be.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeocodingService {

    private final String API_KEY = "";
    private final RestTemplate restTemplate = new RestTemplate();

    public double[] getCoordinates(String indirizzo) {
        try {
            String indirizzoCompleto = indirizzo + ", Rende";

            String url = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/geocode/json")
                    .queryParam("address", indirizzoCompleto)
                    .queryParam("key", API_KEY)
                    .toUriString();

            JsonNode response = restTemplate.getForObject(url, JsonNode.class);

            // Verifichiamo lo stato "OK" di Google
            if (response != null && "OK".equals(response.path("status").asText())) {
                JsonNode location = response.path("results").get(0).path("geometry").path("location");

                double lat = location.path("lat").asDouble();
                double lng = location.get("lng").asDouble();

                return new double[]{lat, lng};
            }
        } catch (Exception e) {
            System.err.println("Errore Geocoding: " + e.getMessage());
        }
        return new double[]{39.333, 16.178}; //queste sono di rende
    }
}
package it.unical.demacs.wa.rendeadvisor_be.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeocodingService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${google.api.key}")
    private String apiKey;

    public double[] getCoordinates(String indirizzo) {
        try {
            String indirizzoCompleto = indirizzo + ", Rende";

            String url = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/geocode/json")
                    .queryParam("address", indirizzoCompleto)
                    .queryParam("key", apiKey)
                    .encode() // Aggiungi questo per gestire gli spazi negli indirizzi
                    .toUriString();

            JsonNode response = restTemplate.getForObject(url, JsonNode.class);

            if (response != null && "OK".equals(response.path("status").asText())) {
                JsonNode location = response.path("results").get(0).path("geometry").path("location");
                double lat = location.path("lat").asDouble();
                double lng = location.path("lng").asDouble();
                return new double[]{lat, lng};
            }
        } catch (Exception e) {
            System.err.println("Errore Geocoding: " + e.getMessage());
        }
        return new double[]{39.333, 16.178};
    }
}
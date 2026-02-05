package it.unical.demacs.wa.rendeadvisor_be.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.List;

@Service
public class GeocodingService {

    private static final double CENTRO_LAT = 39.333;
    private static final double CENTRO_LNG = 16.178;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${google.api.key}")
    private String apiKey;

    public double[] getCoordinates(String indirizzo) {
        try {
            String indirizzoCompleto = indirizzo + ", Rende";

            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromUriString("https://maps.googleapis.com/maps/api/geocode/json")
                    .queryParam("address", indirizzoCompleto)
                    .queryParam("key", apiKey)
                    .encode();

            String url = builder.toUriString();

            String raw = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode response = mapper.readTree(raw);

            if (response != null && "OK".equals(response.path("status").asText())) {
                JsonNode location = response.path("results").get(0).path("geometry").path("location");
                double lat = location.path("lat").asDouble();
                double lng = location.path("lng").asDouble();
                return new double[]{lat, lng};
            }

            System.err.println("Geocoding fallito per: " + indirizzoCompleto);

        } catch (Exception e) {
            System.err.println("Errore Geocoding: " + e.getMessage());
        }

        return null;
    }


    private double calcolaDistanza(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public List<AttivitaDTO> ordinaPerDistanzaDalCentro(List<AttivitaDTO> locali) {
        return locali.stream()
                .sorted(Comparator.comparingDouble(
                        l -> calcolaDistanza(CENTRO_LAT, CENTRO_LNG, l.getLatitudine(), l.getLongitudine())
                ))
                .toList();
    }
}
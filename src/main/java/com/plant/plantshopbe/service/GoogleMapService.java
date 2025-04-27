package com.plant.plantshopbe.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j

public class GoogleMapService {
    @Value("${google.maps.api-key}")
    private String apiKey;

    @Value("${google.shop-address}")
    private String shopAddress;

    public double getDistanceInKm(String customerAddress) {
        try {
            String url = String.format(
                    "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&key=%s",
                    URLEncoder.encode(shopAddress, StandardCharsets.UTF_8),
                    URLEncoder.encode(customerAddress, StandardCharsets.UTF_8),
                    apiKey
            );

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
            JsonNode body = response.getBody();

            if (body != null && "OK".equals(body.get("status").asText())) {
                JsonNode element = body.get("rows").get(0).get("elements").get(0);
                if ("OK".equals(element.get("status").asText())) {
                    int distanceInMeters = element.get("distance").get("value").asInt();
                    return distanceInMeters / 1000.0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 100.0; // fallback distance
    }
}

package com.internship.tool.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiServiceClient {

    private final RestTemplate restTemplate;
    private final String AI_SERVICE_URL = "http://localhost:5000/generate-report";

    public AiServiceClient() {
        // Day 4: 10s timeout
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000);
        factory.setReadTimeout(10000);
        this.restTemplate = new RestTemplate(factory);
    }

    // Day 4: RestTemplate calls to Flask endpoints, null return on error
    public String generateReport(String prompt) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> map = new HashMap<>();
            map.put("prompt", prompt);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(map, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(AI_SERVICE_URL, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            // Log error here
            return null;
        }
    }
}

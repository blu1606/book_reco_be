package backend.controller;

import backend.model.GeminiModel;
import backend.model.ModelListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
public class GeminiModelController {

    private static final Logger log = LoggerFactory.getLogger(GeminiModelController.class);
    private final RestTemplate restClient;

    public GeminiModelController(RestTemplate restTemplate) {
        log.info("GeminiModelController...");
        this.restClient = restTemplate;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Book Recommendation API is running");
        return ResponseEntity.ok(response);
    }

    /*
        curl https://generativelanguage.googleapis.com/v1beta/openai/models \                                                                                                                                                                                                  ✔ 10s base 
        -H "Authorization: Bearer GEMINI_API_KEY"
     */
    @GetMapping("/models")
    public ResponseEntity<List<GeminiModel>> models(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        
        log.info("Received models request");
        
        try {
            // Extract API key from Authorization header
            String apiKey = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                apiKey = authorizationHeader.substring(7);
            }
            
            if (apiKey == null || apiKey.trim().isEmpty()) {
                log.warn("Missing API key in models request");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            // Basic validation for Gemini API key format
            if (!apiKey.startsWith("AIza")) {
                log.warn("Invalid API key format in models request");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            
            HttpEntity<String> request = new HttpEntity<>(headers);
            
            ResponseEntity<ModelListResponse> response = restClient.exchange(
                    "https://generativelanguage.googleapis.com/v1beta/openai/models",
                    HttpMethod.GET,
                    request,
                    ModelListResponse.class
            );
            
            if (response.getBody() != null) {
                log.info("Successfully fetched models from Gemini API");
                return ResponseEntity.ok(response.getBody().data());
            } else {
                log.warn("No response body from Gemini API models endpoint");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            
        } catch (Exception e) {
            log.error("Error fetching models from Gemini API", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

package backend.controller;

import backend.model.ChatRequest;
import backend.model.ChatResponse;
import backend.service.BookRecommendationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/books")
public class BookRecommendationController {

    private static final Logger log = LoggerFactory.getLogger(BookRecommendationController.class);
    private final BookRecommendationService bookRecommendationService;

    public BookRecommendationController(BookRecommendationService bookRecommendationService) {
        this.bookRecommendationService = bookRecommendationService;
    }

    @PostMapping("/recommend")
    public ResponseEntity<ChatResponse> getRecommendations(
            @RequestBody ChatRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        
        log.info("Received recommendation request for session: {}", request.sessionChatId);
        
        try {
            // Extract API key from Authorization header
            String apiKey = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                apiKey = authorizationHeader.substring(7);
            }
            
            if (apiKey == null || apiKey.trim().isEmpty()) {
                log.warn("Missing API key in request");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ChatResponse("API key is required"));
            }
            
            // Basic validation for Gemini API key format
            if (!apiKey.startsWith("AIza")) {
                log.warn("Invalid API key format");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ChatResponse("Invalid API key format"));
            }
            
            if (request.prompt == null || request.prompt.trim().isEmpty()) {
                log.warn("Missing prompt in request");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ChatResponse("Prompt is required"));
            }
            
            if (request.sessionChatId == null || request.sessionChatId.trim().isEmpty()) {
                log.warn("Missing session chat ID in request");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ChatResponse("Session chat ID is required"));
            }
            
            String result = bookRecommendationService.getBookRecommendations(
                request.prompt, 
                request.sessionChatId, 
                apiKey
            );
            
            log.info("Successfully processed recommendation request");
            return ResponseEntity.ok(new ChatResponse(result));
            
        } catch (Exception e) {
            log.error("Error processing recommendation request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ChatResponse("Internal server error: " + e.getMessage()));
        }
    }
} 
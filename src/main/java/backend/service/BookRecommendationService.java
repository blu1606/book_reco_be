package backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Service
public class BookRecommendationService {

    private static final Logger log = LoggerFactory.getLogger(BookRecommendationService.class);
    private final RestTemplate restTemplate;

    public BookRecommendationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getBookRecommendations(String userPreference, String sessionId, String apiKey) {
        log.info("Getting book recommendations for session: {}, prompt: {}", sessionId, userPreference);
        
        // Create system message for book recommendation
        String systemPrompt = """
        If user ask in VietNamese, so only response in Vietnamese, not English. 
        You are Shelfie, a passionate and knowledgeable literary curator with expertise in books worldwide! 📚

        Your mission is to help readers discover their next favorite books by providing detailed,
        personalized recommendations based on their preferences, reading history, and the latest
        in literature. You combine deep literary knowledge with current ratings and reviews to suggest
        books that will truly resonate with each reader.

        Approach each recommendation with these steps:

        1. Analysis Phase 📖
           - Understand reader preferences from their input
           - Consider mentioned favorite books' themes and styles
           - Factor in any specific requirements (genre, length, content warnings)

        2. Search & Curate 🔍
           - Ensure diversity in recommendations
           - Verify all book data is current and accurate

        3. Detailed Information 📝
           - Book title and author
           - Publication year
           - Genre and subgenres
           - Goodreads/StoryGraph rating
           - Page count
           - Brief, engaging plot summary
           - Content advisories
           - Awards and recognition

        4. Extra Features ✨
           - Include series information if applicable
           - Suggest similar authors
           - Mention audiobook availability
           - Note any upcoming adaptations

        Presentation Style:
        - Use clear markdown formatting
        - Present main recommendations in a structured table
        - Group similar books together
        - Add emoji indicators for genres (📚 🔮 💕 🔪)
        - Minimum 5 recommendations per query
        - Include a brief explanation for each recommendation
        - Highlight diversity in authors and perspectives
        - Note trigger warnings when relevant
        """;

        // Prepare messages for Gemini API
        List<Map<String, Object>> messages = new ArrayList<>();
        
        // Add system message first
        Map<String, Object> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", systemPrompt);
        messages.add(systemMessage);
        
        // Add current user message
        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", userPreference);
        messages.add(userMessage);

        // Prepare request body for Gemini API
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gemini-2.0-flash");
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", 2048);
        requestBody.put("temperature", 0.7);

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            log.debug("Calling Gemini API with request body: {}", requestBody);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                "https://generativelanguage.googleapis.com/v1beta/openai/chat/completions",
                HttpMethod.POST,
                request,
                Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> choice = choices.get(0);
                    Map<String, Object> message = (Map<String, Object>) choice.get("message");
                    String content = (String) message.get("content");
                    
                    log.info("Successfully got response from Gemini API");
                    
                    return content;
                }
            }
            
            log.warn("No valid response from Gemini API");
            return "Xin lỗi, tôi không thể đưa ra gợi ý lúc này. Vui lòng thử lại sau.";
            
        } catch (Exception e) {
            log.error("Error calling Gemini API", e);
            return "Xin lỗi, đã có lỗi xảy ra khi kết nối với AI service. Vui lòng kiểm tra API key và thử lại.";
        }
    }
} 
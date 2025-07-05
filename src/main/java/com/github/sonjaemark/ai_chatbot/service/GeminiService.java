package com.github.sonjaemark.ai_chatbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.endpoint}")
    private String endpoint;

    private final ChatMemoryManager memoryManager;

    public GeminiService(ChatMemoryManager memoryManager) {
        this.memoryManager = memoryManager;
    }

    
    public String chat(String sessionId, String userPrompt) {
        memoryManager.addUserMessage(sessionId, userPrompt);

        Map<String, Object> body = Map.of("contents", memoryManager.getHistory(sessionId));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            endpoint + "?key=" + apiKey,
            HttpMethod.POST,
            request,
            new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {}
        );

        String reply = "No response";
        Map<String, Object> responseBody = response.getBody();
        if (response.getStatusCode().is2xxSuccessful() && responseBody != null) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> first = candidates.get(0);
                Object contentObj = first.get("content");
                if (contentObj instanceof Map) {
                    Map<?, ?> contentMap = (Map<?, ?>) contentObj;
                    Object partsObj = contentMap.get("parts");
                    if (partsObj instanceof List) {
                        List<?> partsList = (List<?>) partsObj;
                        if (!partsList.isEmpty() && partsList.get(0) instanceof Map) {
                            Map<?, ?> partMap = (Map<?, ?>) partsList.get(0);
                            Object textObj = partMap.get("text");
                            if (textObj instanceof String) {
                                reply = (String) textObj;
                                memoryManager.addGeminiMessage(sessionId, reply);
                            }
                        }
                    }
                }
            }
        }

        return reply;
    }

    public void reset(String sessionId) {
        memoryManager.resetHistory(sessionId);
    }


    public void uploadContext(String sessionId, String context) {
        chat(sessionId, context);
    }

}
package com.github.sonjaemark.ai_chatbot.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.sonjaemark.ai_chatbot.service.GeminiService;

@RestController
@RequestMapping("/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestParam String prompt, @RequestParam(required = false) String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            sessionId = UUID.randomUUID().toString();  // generate a new one
        }
        String response = geminiService.chat(sessionId, prompt);
        
        Map<String, String> result = new HashMap<>();
        result.put("sessionId", sessionId);
        result.put("response", response);

        return result;
    }

    @PostMapping("/reset")
    public Map<String, Object> reset(@RequestParam String sessionId) {
        geminiService.reset(sessionId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Session reset.");
        response.put("sessionId", sessionId);

        return response;
    }

    @PostMapping("/upload-context")
    public Map<String, Object> uploadContext(@RequestBody String context,
                                             @RequestParam String instruction) {
        String sessionId = UUID.randomUUID().toString();
        String additionalContext = "Context: " + context + "\nInstruction: " + instruction;

        geminiService.uploadContext(sessionId, additionalContext);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Context uploaded.");
        response.put("sessionId", sessionId);

        return response;
    }
}

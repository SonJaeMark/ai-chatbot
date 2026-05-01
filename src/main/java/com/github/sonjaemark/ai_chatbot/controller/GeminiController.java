package com.github.sonjaemark.ai_chatbot.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

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
    public Map<String, Object> uploadContext(@RequestBody(required = false) String context,
                                             @RequestParam String instruction) {
        if (instruction == null || instruction.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "instruction must not be blank.");
        }
        if (context == null || context.isBlank()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Context body is required. Send raw text in the request body (Content-Type: text/plain)."
            );
        }

        String sessionId = UUID.randomUUID().toString();
        String additionalContext = "Context: " + context + "\nInstruction: " + instruction;

        try {
            geminiService.uploadContext(sessionId, additionalContext);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (IllegalStateException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, ex.getMessage(), ex);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Context uploaded.");
        response.put("sessionId", sessionId);

        return response;
    }
}

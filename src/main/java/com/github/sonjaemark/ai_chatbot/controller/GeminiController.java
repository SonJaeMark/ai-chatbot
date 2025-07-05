package com.github.sonjaemark.ai_chatbot.controller;

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
    public String chat(@RequestParam String prompt, @RequestParam(required = false) String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            sessionId = UUID.randomUUID().toString();  // generate a new one
        }
        String response = geminiService.chat(sessionId, prompt);
        return "Session: " + sessionId + "\nGemini:\n" + response;
    }

    @PostMapping("/reset")
    public String reset(@RequestParam String sessionId) {
        geminiService.reset(sessionId);
        return "Session " + sessionId + " reset.";
    }

    @PostMapping("/upload-context")
    public String uploadContext(@RequestBody String context, @RequestParam String instruction) {
        String sessionId = UUID.randomUUID().toString();

        String additionalContext = "Context: " + context + "\nInstruction: " + instruction;

        geminiService.uploadContext(sessionId, additionalContext);
        return sessionId;
    }
}

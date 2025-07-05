package com.github.sonjaemark.ai_chatbot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ChatMemoryManager {

    // Key: sessionId or userId, Value: conversation history
    private final Map<String, List<Map<String, Object>>> memoryMap = new HashMap<>();

    public void addUserMessage(String sessionId, String text) {
        memoryMap
            .computeIfAbsent(sessionId, k -> new ArrayList<>())
            .add(Map.of("role", "user", "parts", List.of(Map.of("text", text))));
    }

    public void addGeminiMessage(String sessionId, String text) {
        memoryMap
            .computeIfAbsent(sessionId, k -> new ArrayList<>())
            .add(Map.of("role", "model", "parts", List.of(Map.of("text", text))));
    }

    public List<Map<String, Object>> getHistory(String sessionId) {
        return memoryMap.getOrDefault(sessionId, new ArrayList<>());
    }

    public void resetHistory(String sessionId) {
        memoryMap.remove(sessionId);
    }
}
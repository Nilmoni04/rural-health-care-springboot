package com.ruralhealthcare.RHCW.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent?key=";

    private static final int MAX_MESSAGES = 20;
    private static final int MAX_CONTENT_LENGTH = 2000;

    private static final String SYSTEM_PROMPT =
            "You are HealthBot, a helpful rural healthcare assistant for a Rural Health Care platform in India. " +
                    "You help patients with health questions, first aid, medical terms, and guiding them to the right service. " +
                    "If someone describes a medical emergency, always tell them to call an ambulance immediately. " +
                    "Always remind users that your advice is general and they should consult a doctor for diagnosis and treatment.";

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, Object> body) {

        List<?> rawMessages = (List<?>) body.get("messages");

        if (rawMessages == null || rawMessages.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("reply", "No messages provided."));
        }

        // ✅ Convert messages to Gemini format
        List<Map<String, Object>> contents = rawMessages.stream()
                .filter(m -> m instanceof Map)
                .map(m -> (Map<?, ?>) m)
                .filter(m -> {
                    Object role = m.get("role");
                    Object content = m.get("content");

                    return ("user".equals(role) || "assistant".equals(role))
                            && content instanceof String
                            && ((String) content).length() <= MAX_CONTENT_LENGTH;
                })
                .limit(MAX_MESSAGES)
                .map(m -> {
                    String role = "assistant".equals(m.get("role")) ? "model" : "user";

                    return Map.<String, Object>of(
                            "role", role,
                            "parts", List.of(Map.of("text", m.get("content")))
                    );
                })
                .collect(Collectors.toList());

        if (contents.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("reply", "Invalid message format."));
        }

        // ✅ FIX: Add system prompt as first message (NOT system_instruction)
        List<Map<String, Object>> finalContents = new ArrayList<>();

        finalContents.add(Map.of(
                "role", "user",
                "parts", List.of(Map.of("text", SYSTEM_PROMPT))
        ));

        finalContents.addAll(contents);

        // ✅ Correct request body for v1
        Map<String, Object> requestBody = Map.of(
                "contents", finalContents,
                "generationConfig", Map.of(
                        "temperature", 0.7,
                        "maxOutputTokens", 500
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            String url = GEMINI_URL + geminiApiKey;

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(url, request, Map.class);

            Map responseBody = response.getBody();
            String reply = "Sorry, I could not get a response.";

            // ✅ Safe parsing
            if (responseBody != null && responseBody.get("candidates") instanceof List<?> candidates
                    && !candidates.isEmpty()) {

                Object first = candidates.get(0);

                if (first instanceof Map<?, ?> candidate) {
                    Object content = candidate.get("content");

                    if (content instanceof Map<?, ?> contentMap
                            && contentMap.get("parts") instanceof List<?> parts
                            && !parts.isEmpty()
                            && parts.get(0) instanceof Map<?, ?> part) {

                        reply = (String) part.get("text");
                    }
                }
            }

            return ResponseEntity.ok(Map.of("reply", reply));

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("reply", "Error: " + e.getMessage()));
        }
    }
}
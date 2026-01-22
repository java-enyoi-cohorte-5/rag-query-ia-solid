package com.enyoi.ragquery.ia.infrastructure.gemini;

import com.enyoi.ragquery.ia.domain.port.ChatService;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;


public class GeminiChatService implements ChatService {

    private static final String MODEL = "gemini-2.0-flash";

    private final GeminiClientWrapper client;

    public GeminiChatService(GeminiClientWrapper client) {
        this.client = client;
    }

    @Override
    public String chatWithSystemPrompt(String systemPrompt, String userMessage) {
        Content systemInstruction = Content.fromParts(Part.fromText(systemPrompt));

        GenerateContentResponse response = client.generateContent(
                MODEL,
                userMessage,
                GenerateContentConfig.builder()
                        .systemInstruction(systemInstruction)
                        .temperature(0.7f)
                        .maxOutputTokens(2048)
                        .build()
        );
        return response.text();
    }
}

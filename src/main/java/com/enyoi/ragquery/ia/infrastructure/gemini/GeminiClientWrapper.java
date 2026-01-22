package com.enyoi.ragquery.ia.infrastructure.gemini;

import com.google.genai.Client;
import com.google.genai.types.EmbedContentConfig;
import com.google.genai.types.EmbedContentResponse;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

public class GeminiClientWrapper {
    private final Client client;

    public GeminiClientWrapper(Client client) {
        this.client = client;
    }

    public GenerateContentResponse generateContent(String model, String message, GenerateContentConfig config) {
        return client.models.generateContent(
                model,
                message,
                config
        );
    }

    public EmbedContentResponse embedContent(String model, String text, EmbedContentConfig config) {
        return client.models.embedContent(
                model,
                text,
                EmbedContentConfig.builder().build()
        );
    }



}

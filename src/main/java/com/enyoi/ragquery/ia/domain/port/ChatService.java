package com.enyoi.ragquery.ia.domain.port;

public interface ChatService {
    String chatWithSystemPrompt(String systemPrompt, String userMessage);
}

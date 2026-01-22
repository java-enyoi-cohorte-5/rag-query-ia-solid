package com.enyoi.ragquery.ia.infrastructure.gemini;

import com.enyoi.ragquery.ia.domain.port.EmbeddingService;
import com.google.genai.types.EmbedContentConfig;
import com.google.genai.types.EmbedContentResponse;

import java.util.ArrayList;
import java.util.List;

public class GeminiEmbeddingService implements EmbeddingService {

    private static final String EMBEDDING_MODEL = "text-embedding-004";
    private static final int EMBEDDING_DIMENSIONS = 768;

    private final GeminiClientWrapper client;

    public GeminiEmbeddingService(GeminiClientWrapper client) {
        this.client = client;
    }

    @Override
    public List<Float> embed(String text) {
        EmbedContentResponse response = client.embedContent(
                EMBEDDING_MODEL,
                text,
                EmbedContentConfig.builder().build()
        );

        // Extraer el embedding de la respuesta
        // El SDK devuelve embeddings() como lista, tomamos el primero
        if (response.embeddings().isPresent() && !response.embeddings().get().isEmpty()) {
            return response.embeddings().get().get(0).values().orElse(List.of());
        }
        return new ArrayList<>();
    }

    @Override
    public List<List<Float>> embedBatch(List<String> texts) {
        List<List<Float>> embeddings = new ArrayList<>();
        for (String text : texts) {
            embeddings.add(embed(text));
        }
        return embeddings;
    }

    @Override
    public int getDimensions() {
        return EMBEDDING_DIMENSIONS;
    }
}

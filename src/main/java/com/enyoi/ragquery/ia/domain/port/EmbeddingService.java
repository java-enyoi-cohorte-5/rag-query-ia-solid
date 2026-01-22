package com.enyoi.ragquery.ia.domain.port;

import java.util.List;

public interface EmbeddingService {

    /**
     * Genera embedding para un texto.
     * @param text texto a convertir en vector
     * @return lista de floats representando el embedding
     */
    List<Float> embed(String text);

    /**
     * Genera embeddings para múltiples textos.
     * @param texts lista de textos
     * @return lista de embeddings
     */
    List<List<Float>> embedBatch(List<String> texts);

    /**
     * Obtiene la dimensión del modelo de embedding.
     * @return número de dimensiones del vector
     */
    int getDimensions();
}

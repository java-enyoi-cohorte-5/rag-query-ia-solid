package com.enyoi.ragquery.ia.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@DisplayName("Document Model Domain Test")
public class DocumentTest {
    // @Test
    // @DisplayName("Validar que sea un record y pida los diferentes elementos id, content, metadata, embedding")
    // void testValidarRecordParametrosDocument() {
    //     Map<String, Object> metadata =Map.of("topic", "some-topic", "source", "some source");
    //     List<Float> embedding = List.of(0.5f, 0.7f);
    //     Supplier<Document> documentSupplier = () -> new Document("some-id",
    //             "some content",
    //             metadata,
    //             embedding);
    //     assertAll(
    //             () -> assertDoesNotThrow(documentSupplier::get),
    //             () -> assertThat(documentSupplier.get()).isInstanceOf(Document.class),
    //             () -> assertThat(documentSupplier.get()).hasFieldOrPropertyWithValue("id", "some-id"),
    //             () -> assertThat(documentSupplier.get()).hasFieldOrPropertyWithValue("content", "some content"),
    //             () -> assertThat(documentSupplier.get()).hasFieldOrPropertyWithValue("metadata", metadata),
    //             () -> assertThat(documentSupplier.get()).hasFieldOrPropertyWithValue("embedding", embedding)
    //     );
    // }

    // @Test
    // @DisplayName("Validar que pueda sacar los valores de metadata con la key")
    // void testValidarMetodoMetadataValueNoNulos() {
    //     Map<String, Object> metadata =Map.of("topic", "some-topic", "source", "some source");
    //     List<Float> embedding = List.of(0.5f, 0.7f);
    //     Document document =new Document("some-id", "some content",
    //             metadata, embedding);

    //     assertAll(
    //             () -> assertDoesNotThrow(() -> document.getMetadataValue("topic")),
    //             () -> assertThat(document.getMetadataValue("topic")).isEqualTo("some-topic"),
    //             () -> assertThat(document.getMetadataValue("source")).isEqualTo("some source")
    //     );
    // }

    // @Test
    // @DisplayName("Validar que cuando el metadata key no tenga valor el resultado sea nulo")
    // void testValidarMetodoMetadataValueNulos() {
    //     Map<String, Object> metadata = Map.of();
    //     List<Float> embedding = List.of(0.5f, 0.7f);
    //     Document document =new Document("some-id", "some content",
    //             metadata, embedding);

    //     assertAll(
    //             () -> assertDoesNotThrow(() -> document.getMetadataValue("topic")),
    //             () -> assertThat(document.getMetadataValue("topic")).isNull(),
    //             () -> assertThat(document.getMetadataValue("source")).isNull()
    //     );
    // }
}

package com.enyoi.ragquery.ia.main;

// import com.enyoi.ragquery.ia.application.RAGQueryUseCase;
// import com.enyoi.ragquery.ia.domain.model.Document;
// import com.enyoi.ragquery.ia.infrastructure.data.SolidArchitectureDocuments;
// import com.enyoi.ragquery.ia.infrastructure.gemini.GeminiChatService;
// import com.enyoi.ragquery.ia.infrastructure.gemini.GeminiClientWrapper;
// import com.enyoi.ragquery.ia.infrastructure.gemini.GeminiEmbeddingService;
// import com.enyoi.ragquery.ia.infrastructure.memory.InMemoryVectorStore;
// import com.google.genai.Client;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RAGMain {
    // public static void main(String[] args) {
    //     System.out.println();
    //     System.out.println("RAG CON GEMINI");
    //     System.out.println();

    //     String apiKey = System.getenv("GOOGLE_API_KEY");

    //     if (apiKey == null || apiKey.isBlank()) {
    //         System.out.println("Error: Debes configurar la variable de entorno GOOGLE_API_KEY");
    //         System.out.println("\tEjecuta: export GOOGLE_API_KEY");
    //         System.exit(1);
    //     }

    //     System.out.println("Inicializando el sistema RAG...");
    //     System.out.println("\t(Indexando y generando embeddings puede tomar un momento)");
    //     System.out.println();

    //     Client client = Client.builder().apiKey(apiKey).build();
    //     GeminiClientWrapper geminiClientWrapper = new GeminiClientWrapper(client);

    //     GeminiEmbeddingService embeddingService = new GeminiEmbeddingService(geminiClientWrapper);
    //     InMemoryVectorStore vectorStore = new InMemoryVectorStore(embeddingService);
    //     GeminiChatService chatService = new GeminiChatService(geminiClientWrapper);

    //     RAGQueryUseCase ragUseCase = new RAGQueryUseCase(embeddingService, vectorStore, chatService);

    //     System.out.println("Cagando base de conocimiento sobre SOLID y arquitectura Hexagonal...");
    //     loadKnowledgeBase(ragUseCase);
    //     System.out.println("Base de conocimiento cargada");
    //     System.out.println();

    //     Scanner scanner = new Scanner(System.in);

    //     while(true) {
    //         System.out.println();
    //         System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
    //         System.out.println("│  Elige una opción:                                          │");
    //         System.out.println("│  1. Consulta RAG simple                                     │");
    //         System.out.println("│  2. Consulta RAG con fuentes                                │");
    //         System.out.println("│  3. Agregar documento personalizado                         │");
    //         System.out.println("│  4. Ver documentos disponibles                              │");
    //         System.out.println("│  5. Salir                                                   │");
    //         System.out.println("└─────────────────────────────────────────────────────────────┘");
    //         System.out.print("Opción: ");

    //         String option = scanner.nextLine().trim();

    //         switch (option) {
    //             case "1" -> queryRAG(ragUseCase, scanner);
    //             case "2" -> queryRAGWithSources(ragUseCase, scanner);
    //             case "3" -> addDocument(ragUseCase, scanner);
    //             case "4" -> showDocuments();
    //             case "5" -> {
    //                 System.out.println("Cerrando sistema RAG");
    //                 scanner.close();
    //                 System.exit(0);
    //             }
    //             default -> System.out.println("Opción no valida");
    //         }
    //     }
    // }

    // private static void loadKnowledgeBase(RAGQueryUseCase ragUseCase) {
    //     List<SolidArchitectureDocuments.DocumentData> documents = SolidArchitectureDocuments.getAllDocuments();
    //     int count = 0;

    //     for (SolidArchitectureDocuments.DocumentData doc : documents) {
    //         try {
    //             ragUseCase.ingestDocument(doc.content(), doc.metadata());
    //             count++;
    //             System.out.print(".");  // Progreso visual
    //         } catch (Exception e) {
    //             String title = (String) doc.metadata().get("title");
    //             System.err.println("\nError cargando documento: " + title);
    //         }
    //     }
    //     System.out.println("\nDocumentos cargados: " + count);
    // }

    // private static void queryRAG(RAGQueryUseCase ragUseCase, Scanner scanner) {
    //     System.out.println("\nCONSULTA RAG SIMPLE");
    //     System.out.println("─────────────────────────────────────────────────");
    //     System.out.println("Ejemplos de preguntas:");
    //     System.out.println("  - ¿Qué es el principio de responsabilidad única?");
    //     System.out.println("  - ¿Cómo funciona la arquitectura hexagonal?");
    //     System.out.println("  - ¿Cuál es la diferencia entre OCP y LSP?");
    //     System.out.println();
    //     System.out.print("Tu pregunta: ");
    //     String question = scanner.nextLine();

    //     if (question.isBlank()) {
    //         System.out.println("La pregunta no puede estar vacía");
    //         return;
    //     }

    //     System.out.println("\nBuscando documentos relevantes y generando respuesta...\n");

    //     try {
    //         String response = ragUseCase.query(question);
    //         System.out.println("Respuesta basada en la base de conocimiento:");
    //         System.out.println("─────────────────────────────────────────────────");
    //         System.out.println(response);
    //     } catch (Exception e) {
    //         System.err.println("Error: " + e.getMessage());
    //     }
    // }

    // private static void queryRAGWithSources(RAGQueryUseCase ragUseCase, Scanner scanner) {
    //     System.out.println("\nCONSULTA RAG CON FUENTES");
    //     System.out.println("─────────────────────────────────────────────────");
    //     System.out.println("(Muestra los documentos usados para generar la respuesta)");
    //     System.out.print("Tu pregunta: ");
    //     String question = scanner.nextLine();

    //     if (question.isBlank()) {
    //         System.out.println("La pregunta no puede estar vacía");
    //         return;
    //     }

    //     System.out.println("\nBuscando documentos relevantes...\n");

    //     try {
    //         RAGQueryUseCase.RAGResult response = ragUseCase.queryWithSources(question);

    //         System.out.println("DOCUMENTOS RELEVANTES ENCONTRADOS:");
    //         System.out.println("─────────────────────────────────────────────────");
    //         for (Document doc : response.sourceDocs()) {
    //             System.out.println("\tID: " + doc.id());
    //             if (doc.metadata().containsKey("title")) {
    //                 System.out.println("\tTítulo: " + doc.metadata().get("title"));
    //             }
    //             System.out.println("\tFragmento: " + doc.content().substring(0, Math.min(100, doc.content().length())) + "...");
    //             System.out.println();
    //         }

    //         System.out.println("RESPUESTA GENERADA:");
    //         System.out.println("─────────────────────────────────────────────────");
    //         System.out.println(response.answer());
    //     } catch (Exception e) {
    //         System.err.println("Error: " + e.getMessage());
    //     }
    // }

    // private static void addDocument(RAGQueryUseCase ragUseCase, Scanner scanner) {
    //     System.out.println("\nAGREGAR DOCUMENTO PERSONALIZADO");
    //     System.out.println("─────────────────────────────────────────────────");

    //     System.out.print("ID del documento: ");
    //     String id = scanner.nextLine();

    //     System.out.print("Título: ");
    //     String title = scanner.nextLine();

    //     System.out.println("Contenido (termina con una línea vacía):");
    //     StringBuilder content = new StringBuilder();
    //     String line;
    //     while (!(line = scanner.nextLine()).isEmpty()) {
    //         content.append(line).append("\n");
    //     }

    //     if (id.isBlank() || title.isBlank() || content.isEmpty()) {
    //         System.out.println("Todos los campos son requeridos");
    //         return;
    //     }

    //     System.out.println("\nGenerando embeddings y almacenando...");

    //     try {
    //         Map<String, Object> metadata = Map.of("title", title, "category", "personalizado");
    //         ragUseCase.ingestDocument(content.toString(), metadata);
    //         System.out.println("Documento agregado exitosamente");
    //     } catch (Exception e) {
    //         System.err.println("Error: " + e.getMessage());
    //     }
    // }

    // private static void showDocuments() {
    //     System.out.println("\nDOCUMENTOS EN LA BASE DE CONOCIMIENTO:");
    //     System.out.println("─────────────────────────────────────────────────");

    //     List<SolidArchitectureDocuments.DocumentData> documents = SolidArchitectureDocuments.getAllDocuments();
    //     for (SolidArchitectureDocuments.DocumentData doc : documents) {
    //         String title = (String) doc.metadata().get("topic");
    //         String category = (String) doc.metadata().get("category");
    //         System.out.println(title + " [" + category + "]");
    //     }
    //     System.out.println("\nTotal: " + documents.size() + " documentos");
    // }
}

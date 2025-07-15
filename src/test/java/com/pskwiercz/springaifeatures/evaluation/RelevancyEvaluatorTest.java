package com.pskwiercz.springaifeatures.evaluation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.document.Document;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RelevancyEvaluatorTest {

    @Autowired
    ChatClient.Builder chatBuilder;

    RelevancyEvaluator relevancyEvaluator;

    private final String QUERY = "What is the capital of France?";

    private final List<String> CONTEXT = List.of(
            "France is a country in Western Europe known for its culture and cuisine.",
            "The capital city of France is Paris, which is also its largest city.",
            "French is the official language spoken in France."
    );

    @BeforeEach
    void setUp() {
        relevancyEvaluator = new RelevancyEvaluator(chatBuilder);
    }

    @Test
    public void testRelevancy() {
        String llmResponse = "The capital of France is Paris.";
        EvaluationRequest request = new EvaluationRequest(QUERY, contextStringToDocuments(CONTEXT), llmResponse);
        EvaluationResponse response = relevancyEvaluator.evaluate(request);

        assertTrue(response.isPass(), "Response feedback: " + response.getFeedback()
                                      + " score: " + response.getScore());
    }

    @Test
    void testIrrelevantResponse() {
        String llmResponse = "I enjoy eating Italian pasta."; // Clearly irrelevant
        EvaluationRequest request = new EvaluationRequest(QUERY, contextStringToDocuments(CONTEXT), llmResponse);
        EvaluationResponse response = relevancyEvaluator.evaluate(request);

        assertFalse(response.isPass(), "Response should be irrelevant. Feedback: " + response.getFeedback());
    }

    private List<Document> contextStringToDocuments(List<String> context) {
        return context.stream()
                .map(Document::new)
                .toList();
    }
}

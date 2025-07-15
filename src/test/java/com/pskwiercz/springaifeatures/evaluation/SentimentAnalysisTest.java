package com.pskwiercz.springaifeatures.evaluation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SentimentAnalysisTest {

    @Autowired
    ReviewService reviewService;

    @Test
    void testPositive() {
        String review  = "This movie was absolutely fantastic! The acting was superb and the plot was engaging.";
        Sentiment sentiment = reviewService.classifySentiment(review);

        assertEquals(Sentiment.POSITIVE, sentiment);
    }

    @Test
    void testNegative() {
        String review  = "This movie was terrible. The acting was bad and the plot was boring.";
        Sentiment sentiment = reviewService.classifySentiment(review);

        assertEquals(Sentiment.NEGATIVE, sentiment);
    }

    @Test
    void testNeutral() {
        String review  = "This movie was okay. It had its moments, but overall it was just average.";
        Sentiment sentiment = reviewService.classifySentiment(review);

        assertEquals(Sentiment.NEUTRAL, sentiment);
    }
}

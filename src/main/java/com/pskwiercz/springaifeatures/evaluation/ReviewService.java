package com.pskwiercz.springaifeatures.evaluation;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    public final ChatClient chatClient;

    public ReviewService(ChatClient.Builder chatBuilder) {
        this.chatClient = chatBuilder
                .defaultOptions(OpenAiChatOptions.builder().temperature(0.1d).build())
                .build();
    }

    public Sentiment classifySentiment(String review) {
        String prompt = """
                Classify the sentiment of following text as POSITIVE, NEGATIVE or NEUTRAL.
                Your response must be only one of these words.
                """;

        return chatClient.prompt()
                .system(prompt)
                .user(review)
                .call()
                .entity(Sentiment.class);
    }

}

package com.pskwiercz.springaifeatures;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ClientTestConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder chatBuilder) {
        return chatBuilder.build();
    }
}

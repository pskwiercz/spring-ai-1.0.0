package com.pskwiercz.springaifeatures.controller;

import com.pskwiercz.springaifeatures.model.Movie;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StructuredOutputController {

    private final ChatClient chatClient;

    public StructuredOutputController(ChatClient.Builder chatBuilder) {
        this.chatClient = chatBuilder.build();
    }

    @GetMapping("/unstructured")
    public String unstructuredOutput() {
        return chatClient
                .prompt()
                .user("Give me list of 10 most popular movies in 2023 with their director and actors")
                .call()
                .content();
    }

    @GetMapping("/structured")
    public List<Movie> structuredOutput() {
        return chatClient
                .prompt()
                .user("Give me list 10 most popular movies in 2023 with their director and actors")
                .call()
                .entity(new ParameterizedTypeReference<List<Movie>>() {});
    }
}

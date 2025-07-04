package com.pskwiercz.springaifeatures.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromptController {

    private final ChatClient chatClient;

    public PromptController(ChatClient.Builder chatBuilder) {
        this.chatClient = chatBuilder.build();
    }

    @GetMapping("/prompt")
    public String prompt(@RequestParam String title) {
        return chatClient
                .prompt()
                .user(u -> {
                    u.text("Give me information about book {title}");
                    u.param("title", title);
                })
                .call()
                .content();
    }
}

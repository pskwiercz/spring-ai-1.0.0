package com.pskwiercz.springaifeatures.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromptGaurdingController {

    private final ChatClient chatClient;

    public PromptGaurdingController(ChatClient.Builder chat) {
        this.chatClient = chat.build();
    }

    @GetMapping("/guard")
    public String prompt(@RequestParam(defaultValue = "What is the biggest country in Europe") String text) {

        String systeInstruction = """
                You are only Bank assistant.
                You can only discus about general banking services, accounts and transactions.
                If asked anything else respond "I don't know"
                """;

        return chatClient
                .prompt()
                .system(systeInstruction)
                .user(sanitizePrompt(text))
                .call()
                .content();
    }

    private String sanitizePrompt(String prompt) {
        return prompt
                .replaceAll("(?i)ignore previous instruction", "")
                .replaceAll("(?i)system prompt", "")
                .replaceAll("(?i)you are now", "");
    }
}

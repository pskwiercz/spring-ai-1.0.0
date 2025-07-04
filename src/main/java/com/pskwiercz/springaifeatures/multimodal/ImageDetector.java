package com.pskwiercz.springaifeatures.multimodal;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageDetector {

    @Value("classpath:/images/bikeSea.webp")
    Resource image;

    private final ChatClient chatClient;

    public ImageDetector(ChatClient.Builder chatBuilder) {
        this.chatClient = chatBuilder.build();
    }

    @GetMapping("/img2txt")
    public String detectImage() {
        return chatClient
                .prompt()
                .user( u -> {
                    u.text("Please describe what you see in image below");
                    u.media(MimeTypeUtils.IMAGE_JPEG, image);
                })
                .call()
                .content();
    }
}

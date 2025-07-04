package com.pskwiercz.springaifeatures.multimodal;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ImageGenerator {

    private final ImageModel imageModel;

    public ImageGenerator(OpenAiImageModel openAiImageModel) {
        this.imageModel = openAiImageModel;
    }

    @GetMapping("/imggen")
    public ResponseEntity<Map<String, String>> generateImage(
            @RequestParam(defaultValue = "Summer sunset on sea") String prompt) {

        ImageOptions imageOptions = OpenAiImageOptions.builder()
                .model("dall-e-3")
                .width(1024)
                .height(1024)
                .quality("hd")
                .style("vivid") // "natural"
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(prompt, imageOptions);
        ImageResponse response = imageModel.call(imagePrompt);

        String url = response.getResult().getOutput().getUrl();

        return  ResponseEntity.ok(Map.of(
                "prompt", prompt,
                "url", url));
    }

}

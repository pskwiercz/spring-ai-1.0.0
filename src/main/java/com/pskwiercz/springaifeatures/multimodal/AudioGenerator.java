package com.pskwiercz.springaifeatures.multimodal;

import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AudioGenerator {

    private final OpenAiAudioSpeechModel speechModel;

    public AudioGenerator(OpenAiAudioSpeechModel openAiAudioSpeechModel) {
        this.speechModel = openAiAudioSpeechModel;
    }

    @GetMapping("/audiogen")
    public ResponseEntity<byte[]> generateSpeech(@RequestParam(defaultValue = "Hello, how are you") String text) {

        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
                .model("tts-1-hd")
                .voice(OpenAiAudioApi.SpeechRequest.Voice.CORAL)
                .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .speed(1.0f)
                .build();

        SpeechPrompt speechPrompt = new SpeechPrompt(text, options);
        SpeechResponse response = speechModel.call(speechPrompt);

        byte[] file = response.getResult().getOutput();

        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"speech.mps\"")
                .body(file);


    }
}

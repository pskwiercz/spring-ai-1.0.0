package com.pskwiercz.springaifeatures.output;

import com.pskwiercz.springaifeatures.ClientTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(ClientTestConfig.class)
public class StructuredOutputTest {


    @Autowired
    private ChatClient chatClient;

    @Test
    public void testGetItinerary() {
        String destination = "Cleveland, OH";

        Itinerary result = chatClient
                .prompt()
                .user( u -> {
                    u.text("Create a 3-day itinerary for {destination}");
                    u.param("destination", destination);
                })
                .call()
                .entity(Itinerary.class);

        assertNotNull(result);
        assertNotNull(result.itinerary());
        assertFalse(result.itinerary().isEmpty(), "Result should not be empty");
    }

}

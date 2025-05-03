package com.example.shortenurl.controllers;

import com.example.shortenurl.exceptions.LongUrlIsNotValidException;
import com.example.shortenurl.models.UrlResponse;
import com.example.shortenurl.repositories.ShortenUrlRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Complete test coverage of Controller methods is not done due to redundancy. Test coverage of Service methods is
 * sufficient for validating non Controller specific inner logic. Instead, we aim to merely verify the proper return
 * of ResponseEntities from the controller, especially in the context of Advice mediated Exception handling.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShortenUrlControllerTests {
    private final ShortenUrlRepository shortenUrlRepository;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public ShortenUrlControllerTests(
            MockMvc mockMvc,
            ShortenUrlRepository shortenUrlRepository) {
        this.shortenUrlRepository = shortenUrlRepository;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @Order(1)
    void createAndDeleteTest() throws Exception {
        String longUrl = "https://google.com/search?q=dogs";
        String response = mockMvc.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\": \"" + longUrl + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value(longUrl))
                .andReturn().getResponse().getContentAsString();
        UrlResponse urlResponse = objectMapper.readValue(response, UrlResponse.class);
        assertEquals(longUrl, urlResponse.getUrl());
        mockMvc.perform(delete("/shorten/" + urlResponse.getShortcode()))
                .andExpect(status().isOk());
        assertEquals(0, shortenUrlRepository.count()); // sanity check
    }

    @Test
    @Order(2)
    void badRetrieveLongUrlTest() throws Exception {
        String badLongUrl = "https:/google.com/search?q=dogs";
        mockMvc.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\": \"" + badLongUrl + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(new LongUrlIsNotValidException(badLongUrl).getMessage()));
    }

    @Test
    @Order(3)
    void redirectToLongUrlTest() throws Exception {
        String longUrl = "https://google.com/search?q=dogs";
        String response = mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\": \"" + longUrl + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value(longUrl))
                .andReturn().getResponse().getContentAsString();
        UrlResponse urlResponse = objectMapper.readValue(response, UrlResponse.class);
        mockMvc.perform(get("/" + urlResponse.getShortcode()))
                .andExpect(status().isFound());
    }
}

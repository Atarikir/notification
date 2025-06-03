package ru.company.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.company.notification.dto.request.EventRequest;
import ru.company.notification.dto.response.EventResponse;
import ru.company.notification.service.EventService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.mockito.Mockito.when;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"it"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private EventService eventService;

    @Test
    @SneakyThrows
    public void testCreateEvent() {
        EventRequest eventRequest = new EventRequest( "message",
                LocalDateTime.of(LocalDate.of(2025, 6, 3), LocalTime.of(22, 22)));
        EventResponse eventResponse = new EventResponse(UUID.randomUUID(), "message",
                LocalDateTime.of(LocalDate.of(2025, 6, 3), LocalTime.of(22, 22)));

        when(eventService.createEvent(ArgumentMatchers.any(EventRequest.class))).thenReturn(eventResponse);

        mockMvc.perform(post("/api/events")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(eventRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("message"));
    }
}
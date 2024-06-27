package com.upwork.controller;

import com.upwork.model.Cinema;
import com.upwork.model.Seat;
import com.upwork.service.TicketingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketingController.class)
@AutoConfigureMockMvc
public class TicketingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketingService ticketingService;

    @MockBean
    private Cinema cinema;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetNextAvailableTicketWithoutPreferredSeat() throws Exception {

        when(ticketingService.getNextAvailableTicket(any())).thenReturn(new Seat(1, 1));


        ResultActions resultActions = mockMvc.perform(get("/api/tickets/next")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.row").value(1))
                .andExpect(jsonPath("$.col").value(1));


        resultActions.andExpect(jsonPath("$.row").exists());
        resultActions.andExpect(jsonPath("$.col").exists());
    }

    @Test
    public void testGetNextAvailableTicketWithPreferredSeat() throws Exception {
        when(ticketingService.getNextAvailableTicket(any())).thenReturn(new Seat(2, 2));


        ResultActions resultActions = mockMvc.perform(get("/api/tickets/next")
                        .param("row", "2")
                        .param("col", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.row").value(2))
                .andExpect(jsonPath("$.col").value(2));


        resultActions.andExpect(jsonPath("$.row").exists());
        resultActions.andExpect(jsonPath("$.col").exists());
    }

    @Test
    public void testClearSeats() throws Exception {


        ResultActions resultActions = mockMvc.perform(post("/api/tickets/reset")

                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}


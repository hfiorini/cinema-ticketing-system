package com.upwork;

import com.upwork.model.Cinema;
import com.upwork.service.TicketingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CinemaApplication {
    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    @Bean
    public Cinema cinema() {
        return new Cinema(50, 100);
    }

    @Bean
    public TicketingService ticketingService(Cinema cinema) {
        return new TicketingService(cinema);
    }
}


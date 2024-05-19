package kea.exam.xpbowlingbackend.reservation;

import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import kea.exam.xpbowlingbackend.activity.repositories.ActivityRepository;

import kea.exam.xpbowlingbackend.activity.repositories.BowlingLaneRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReservationControllerIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void notNull() {
        assertNotNull(webClient);
    }

    @Test
    void createNewReservationSuccessfully() {

        webClient.post().uri("/reservation").contentType(MediaType.APPLICATION_JSON).bodyValue("""
                        {
                            "name": "Harry",
                            "phoneNumber": "12345678",
                            "participants": 1,
                            "activities": [
                                {
                                   
                                    "startTime": "12:00",
                                    "endTime": "13:00",
                                    "date": "2021-12-24",
                                    "bowlingLanes": [
                                        {
                                            "laneNumber": 1
                                        }
                                    ]
                                }
                            ]
                        }
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().json("""
                                        {
                                        
                            "name": "Harry",
                            "phoneNumber": "12345678",
                            "participants": 1,
                            "activities": [
                                {
                            
                                    "startTime": "12:00:00",
                                    "endTime": "13:00:00",
                                    "date": "2021-12-24",
                                    "bowlingLanes": [
                                        {
                                            "laneNumber": 1
                                        }
                                     ]
                                    }
                                ]
                                        }
                        """);

    }
}

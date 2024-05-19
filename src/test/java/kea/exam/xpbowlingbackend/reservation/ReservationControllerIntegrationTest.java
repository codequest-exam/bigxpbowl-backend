package kea.exam.xpbowlingbackend.reservation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;


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
    void createNewBowlingReservationWithNoDiningOrAirhockey() {
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
                """).exchange().expectStatus().isCreated().expectBody().
                jsonPath("$.activities[0].bowlingLanes[0].laneNumber").isEqualTo(1)
                .jsonPath("$.activities[0].diningTables").doesNotExist().jsonPath("$.activities[0].airhockeyTables").doesNotExist();
    }


    @Test
    void createNewBowlingAndAirhockeyReservationWithNoDining() {
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
                """).exchange().expectStatus().isCreated().expectBody().
                jsonPath("$.activities[0].bowlingLanes[0].laneNumber").isEqualTo(1)
                .jsonPath("$.activities[0].diningTables").doesNotExist().jsonPath("$.activities[0].airhockeyTables").doesNotExist();
    }



    @Test
    void createNewReservationWithAllActivities() {
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
                """).exchange().expectStatus().isCreated().expectBody().
                jsonPath("$.activities[0].bowlingLanes[0].laneNumber").isEqualTo(1)
                .jsonPath("$.activities[0].diningTables").doesNotExist().jsonPath("$.activities[0].airhockeyTables").doesNotExist();
    }
}

package kea.exam.xpbowlingbackend.reservation;

import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import kea.exam.xpbowlingbackend.activity.repositories.ActivityRepository;
import kea.exam.xpbowlingbackend.activity.repositories.BowlingLaneRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReservationControllerIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private BowlingLaneRepository bowlingLaneRepository;

    @Autowired
    private ReservationService reservationService;

    @Test
    void notNull() {
        assertNotNull(webClient);
    }

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
        activityRepository.deleteAll();

        reservationRepository.flush();
        activityRepository.flush();
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
                """).exchange().expectStatus().isCreated().expectBody()
                .jsonPath("$.activities[0].bowlingLanes[0].laneNumber").isEqualTo(1)
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
                            "diningTables": [
                                {
                                    "tableNumber": 1
                                }
                            ],
                            "bowlingLanes": [
                                {
                                    "laneNumber": 1
                                }
                            ]
                        }
                    ]
                }
                """).exchange().expectStatus().isCreated().expectBody()
                .jsonPath("$.activities[0].diningTables[0].tableNumber").isEqualTo(1)
                .jsonPath("$.activities[0].bowlingLanes[0].laneNumber").isEqualTo(1)
                .jsonPath("$.activities[0].airhockeyTables").doesNotExist();
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
                            "diningTables": [
                                {
                                    "tableNumber": 1
                                }
                            ],
                            "bowlingLanes": [
                                {
                                    "laneNumber": 1
                                }
                            ],
                            "airhockeyTables": [
                                {
                                    "tableNumber": 1
                                }
                            ]
                        }
                    ]
                }
                """).exchange().expectStatus().isCreated().expectBody()
             .jsonPath("$.activities[0].diningTables[0].tableNumber").isEqualTo(1)
                .jsonPath("$.activities[0].bowlingLanes[0].laneNumber").isEqualTo(1)
                .jsonPath("$.activities[0].airhockeyTables[0].tableNumber").isEqualTo(1);

    }

    @Test
    void createNewReservationWithNoActivities() {
        webClient.post().uri("/reservation").contentType(MediaType.APPLICATION_JSON).bodyValue("""
                {
                    "name": "Harry",
                    "phoneNumber": "12345678",
                    "participants": 1
                   
                }
                """).exchange().expectStatus().isBadRequest();
    }

    @Test
    void createNewReservationWithActivitiesAsAnEmptyArray() {
        webClient.post().uri("/reservation").contentType(MediaType.APPLICATION_JSON).bodyValue("""
                {
                    "name": "Harry",
                    "phoneNumber": "12345678",
                    "participants": 1,
                   "activities": []
                }
                """).exchange().expectStatus().isBadRequest();
    }

    @Test
    void createDuplicateReservation(){
        List<Reservation> newRes = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            reservationService.createReservation(
            //newRes.add(
            new Reservation("12345678 + " +i, "Harry"+i, 1,
                    List.of(new Activity(LocalTime.of(12,0), LocalTime.of(13,0) , LocalDate.of(2021, 12, 24),
                            List.of(new BowlingLane(false, false, i+1)),
                            null, null)
            )));
        }

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
                        }]
                }
                """).exchange().expectStatus().isBadRequest();
    }

    @Test
    void createReservationWithSomeExistingEntries(){
        for (int i = 0; i < 20; i++) {
            reservationService.createReservation(
                    new Reservation("12345678 + " +i, "Harry"+i, 1,
                            List.of(new Activity(LocalTime.of(12,0), LocalTime.of(13,0) , LocalDate.of(2021, 12, 24),
                                    List.of(new BowlingLane(false, false, i+1)),
                                    null, null)
                            )));
        }

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
                        }]
                }
                """).exchange().expectStatus().isCreated().expectBody().jsonPath("$.activities[0].bowlingLanes[0].laneNumber").isEqualTo(21);
    }
}

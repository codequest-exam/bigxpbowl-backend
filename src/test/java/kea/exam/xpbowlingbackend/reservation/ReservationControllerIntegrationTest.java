package kea.exam.xpbowlingbackend.reservation;

import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.activity.entities.ActivityType;
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
                                    "activityType": "bowling",
                                    "startTime": "12:00",
                                    "endTime": "13:00",
                                    "date": "2021-12-24",
                                    "bookables": [
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
                                        
                                            "firstName": "Harry",
                                            "middleName": "James",
                                            "lastName": "Potter",
                                            "fullName": "Harry James Potter",
                                            "house": "Gryffindor",
                                            "schoolYear": 1
                                        }
                        """);
        // assert
//        Reservation reservation = new Reservation("1234", "name", 4, List.of(
//                new Activity(Objects.requireNonNull(activityTypeRepository.findById("bowling").orElse(null)), LocalTime.of(12, 0), LocalTime.of(13, 0),
//                        LocalDate.of(2021, 12, 24), bowlingLaneRepository.findByLaneNumber(1)
//                )));
//
//        // act
//        long preActivityCount = activityRepository.count();
//        Reservation newReservation = reservationController.createStandardReservation(reservation);
//
//        // assert
//
//        newReservation.equals()
//
//        System.out.println(newReservation);
//        assert newReservation != null;
//        assert newReservation.getId() > 0;
//        assert activityRepository.count() == preActivityCount + 1;


    }
}

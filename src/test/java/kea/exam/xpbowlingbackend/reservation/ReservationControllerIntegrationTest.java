package kea.exam.xpbowlingbackend.reservation;

import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.activity.entities.ActivityType;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import kea.exam.xpbowlingbackend.activity.repositories.ActivityRepository;
import kea.exam.xpbowlingbackend.activity.repositories.ActivityTypeRepository;
import kea.exam.xpbowlingbackend.activity.repositories.BowlingLaneRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReservationControllerIntegrationTest {

    @Autowired
    ReservationController reservationController;

    @Autowired
    BowlingLaneRepository bowlingLaneRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Test
    void createNewReservation() {

        //        private int id;
//        private LocalTime startTime;
//        private LocalTime endTime;
//        private LocalDate date;
//
//        private String activityType;
//        @ManyToMany
//        private List<Bookable> bookables;
        // assert
        Reservation reservation = new Reservation("1234", "name", 4, List.of(
                new Activity(Objects.requireNonNull(activityTypeRepository.findById("bowling").orElse(null)), LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24),
                        bowlingLaneRepository.findByLaneNumber(1)
                )));

        // act
        long activityCount = activityRepository.count();
        Reservation newReservation = reservationController.createStandardReservation(reservation);

        // assert

        System.out.println(newReservation);
        assert newReservation != null;
        assert newReservation.getId() > 0;
        assert activityRepository.count() == activityCount + 1;


    }
}

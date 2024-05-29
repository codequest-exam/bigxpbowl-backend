package kea.exam.xpbowlingbackend.activities;


import kea.exam.xpbowlingbackend.activity.ActivityService;
import kea.exam.xpbowlingbackend.activity.dtos.AllAvailableSlotsForDay;
import kea.exam.xpbowlingbackend.activity.repositories.ActivityRepository;
import kea.exam.xpbowlingbackend.reservation.competition.CompetitionDay;
import kea.exam.xpbowlingbackend.reservation.competition.CompetitionDayRepository;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ActivityServiceIntegrationTest {
    private final ActivityService activityService;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CompetitionDayRepository competitionDayRepository;

    @Autowired
    private RecurringBowlingReservationRepository recurringBowlingReservationRepository;

    public ActivityServiceIntegrationTest() {
        this.activityService = new ActivityService(activityRepository, recurringBowlingReservationRepository, competitionDayRepository);
    }

    @AfterEach
    public void tearDown() {
        activityRepository.deleteAll();
        competitionDayRepository.deleteAll();
        recurringBowlingReservationRepository.deleteAll();
    }

    @Test
    public void getAllAvailabilitiesForDay() {

        competitionDayRepository.save(new CompetitionDay(LocalDate.of(2024, 1, 1)));
        AllAvailableSlotsForDay list = activityService.getAllAvailabilitiesForDay(LocalDate.of(2024, 1, 1));


    }
}

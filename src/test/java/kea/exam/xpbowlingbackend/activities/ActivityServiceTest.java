package kea.exam.xpbowlingbackend.activities;

import kea.exam.xpbowlingbackend.activity.ActivityService;
import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import kea.exam.xpbowlingbackend.activity.repositories.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ActivityServiceTest {

    private ActivityService activityService;
    private ActivityRepository activityRepository;

    @BeforeEach
    public void setUp() {
        activityRepository = Mockito.mock(ActivityRepository.class);
        activityService = new ActivityService(activityRepository);
    }


    @Test
    public void timeSlotAvailable_returnsTrue_whenNoActivitiesOnTheDay() {


        Activity activityToCheck = new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24));
        activityToCheck.setBowlingLanes(List.of(new BowlingLane( false, false, 1)));

        when(activityRepository.findAllByDate(any(LocalDate.class))).thenReturn(List.of());

        assertTrue(activityService.timeSlotAvailable(activityToCheck));
    }

    @Test
    public void timeSlotAvailable_throwsError_when24OtherBowlingActivitiesExistDuringTheTimePeriod() {

        Activity activityToCheck = new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24));
        activityToCheck.setBowlingLanes(List.of(new BowlingLane( false, false, 1)));

        List<Activity> existingActivities = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            existingActivities.add(new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24),
                    List.of(new BowlingLane( false, false, i+1)), null, null));
        }

        when(activityRepository.findAllByDate(any(LocalDate.class))).thenReturn(existingActivities);

//        when(activityRepository.findAllByDate(any(LocalDate.class))).thenReturn(List.of(
//                new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24),
//                        List.of(new BowlingLane( false, false, 1)), null, null)
//        ));


           assertThrows(RuntimeException.class, () -> activityService.timeSlotAvailable(activityToCheck));


    }

    @Test
    public void saveAll_savesAndReturnsAllActivities() {
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        List<Activity> activities = Arrays.asList(activity1, activity2);

        when(activityRepository.saveAll(activities)).thenReturn(activities);

        List<Activity> savedActivities = activityService.saveAll(activities);

        assertTrue(savedActivities.containsAll(activities));
    }
}
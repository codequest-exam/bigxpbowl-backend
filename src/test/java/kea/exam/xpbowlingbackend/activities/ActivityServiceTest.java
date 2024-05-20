package kea.exam.xpbowlingbackend.activities;

import kea.exam.xpbowlingbackend.activity.ActivityService;
import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.activity.entities.AirhockeyTable;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import kea.exam.xpbowlingbackend.activity.entities.DiningTable;
import kea.exam.xpbowlingbackend.activity.repositories.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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
    public void setAvailableTableOrLane_returnsTrue_whenNoActivitiesOnTheDay() {
        Activity activityToCheck = new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24));
        activityToCheck.setBowlingLanes(List.of(new BowlingLane( false, false, 1)));

        when(activityRepository.findAllByDate(any(LocalDate.class))).thenReturn(List.of());

        assertTrue(activityService.setAvailableTableOrLane(activityToCheck));

    }

    @Test
    public void getActivitiesByDate_returnsAllActivitiesOnTheGivenDate() {
        LocalDate date = LocalDate.of(2021, 12, 24);
        List<Activity> activities = Arrays.asList(
                new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), date),
                new Activity( LocalTime.of(13, 0), LocalTime.of(14, 0), date),
                new Activity( LocalTime.of(14, 0), LocalTime.of(15, 0), date)
        );

        when(activityRepository.findAllByDate(date)).thenReturn(activities);

        List<Activity> foundActivities = activityService.getActivitiesByDate(date);

        assertTrue(foundActivities.containsAll(activities));
    }

    @Test
    public void setAvailableTableOrLane_throwsError_when24OtherBowlingActivitiesExistDuringTheTimePeriod() {

        Activity activityToCheck = new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24));
        activityToCheck.setBowlingLanes(List.of(new BowlingLane( false, false, 1)));

        List<Activity> existingActivities = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            existingActivities.add(new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24),
                    List.of(new BowlingLane( false, false, i+1)), null, null));
        }

        when(activityRepository.findAllByDate(any(LocalDate.class))).thenReturn(existingActivities);

        assertThrows(RuntimeException.class, () -> activityService.setAvailableTableOrLane(activityToCheck));
    }

    @Test
    public void setAvailableTableOrLane_throwsError_when23OtherBowlingActivitiesExistDuringTheTimePeriod() {

        Activity activityToCheck = new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24));
        activityToCheck.setBowlingLanes(List.of(new BowlingLane( false, false, 1)));

        List<Activity> existingActivities = new ArrayList<>();

        for (int i = 0; i < 23; i++) {
            existingActivities.add(new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24),
                    List.of(new BowlingLane( false, false, i+1)), null, null));
        }

        when(activityRepository.findAllByDate(any(LocalDate.class))).thenReturn(existingActivities);

        assertTrue(activityService.setAvailableTableOrLane(activityToCheck));
    }

    @Test
    public void setAvailableTableOrLane_throwsError_when12OtherBowlingActivitiesOn4LanesExists() {

        Activity activityToCheck = new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24));
        activityToCheck.setBowlingLanes(List.of(new BowlingLane( false, false, 1)));

        List<Activity> existingActivities = new ArrayList<>();


        for (int i = 0; i < 12; i++) {
            existingActivities.add(new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24),
                    List.of(new BowlingLane( false, false, i+1), new BowlingLane( false, false, i+2), new BowlingLane( false, false, i+3), new BowlingLane( false, false, i+4)),
                    null, null));
        }

        when(activityRepository.findAllByDate(any(LocalDate.class))).thenReturn(existingActivities);

        assertThrows(ResponseStatusException.class, () -> activityService.setAvailableTableOrLane(activityToCheck));
    }

    @Test
    public void setAvailableTableOrLane_returnsTrue_when24ActivitiesOfDifferentKindExist() {

        Activity activityToCheck = new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24));
        activityToCheck.setAirhockeyTables(List.of(new AirhockeyTable( false, 1)));

        List<Activity> existingActivities = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            existingActivities.add(new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24),
                    List.of(new BowlingLane( false, false, i+1)), null, null));
        }

        when(activityRepository.findAllByDate(any(LocalDate.class))).thenReturn(existingActivities);

        assertTrue(activityService.setAvailableTableOrLane(activityToCheck));
    }



    @Test
    public void setAvailableTableOrLane_returnsTrue_whenAMixOfActivitiesExistButNoMoreThanMaxAmount(){
        Activity activityToCheck = new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24));
        activityToCheck.setBowlingLanes(List.of(
                new BowlingLane( false, false, 1), new BowlingLane( false, false, 2)
        ));
        activityToCheck.setAirhockeyTables(List.of(new AirhockeyTable( false, 1)));
        activityToCheck.setDiningTables(List.of(new DiningTable( false, 1)));

        List<Activity> existingActivities = new ArrayList<>();

        for (int i = 0; i < 22; i++) {
            existingActivities.add(new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24),
                    List.of(new BowlingLane( false, false, i+1)), null, null));
        }

        for (int i = 0; i < 5; i++) {
            existingActivities.add(new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24),
                    null,null, List.of(new AirhockeyTable( false, i+1))));
        }

        for (int i = 0; i < 19; i++) {
            existingActivities.add(new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24),
                    null,  List.of(new DiningTable( false, i+1)), null));
        }

        when(activityRepository.findAllByDate(any(LocalDate.class))).thenReturn(existingActivities);

        assertTrue(activityService.setAvailableTableOrLane(activityToCheck));
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
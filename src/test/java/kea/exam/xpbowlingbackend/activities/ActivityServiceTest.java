package kea.exam.xpbowlingbackend.activity;

import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.activity.repositories.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ActivityServiceTest {

    @Mock
    ActivityRepository activityRepository;

    @InjectMocks
    ActivityService activityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllActivities() {
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        when(activityRepository.findAll()).thenReturn(Arrays.asList(activity1, activity2));

        List<Activity> result = activityService.getAllActivities();

        assertEquals(2, result.size());
        verify(activityRepository, times(1)).findAll();
    }

    @Test
    void saveAll() {
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        when(activityRepository.saveAll(anyList())).thenReturn(Arrays.asList(activity1, activity2));

        List<Activity> result = activityService.saveAll(Arrays.asList(activity1, activity2));

        assertEquals(2, result.size());
        verify(activityRepository, times(1)).saveAll(anyList());
    }

    @Test
    void getActivityById() {
        Activity activity = new Activity();
        when(activityRepository.findById(anyInt())).thenReturn(Optional.of(activity));

        Optional<Activity> result = activityService.getActivityById(1);

        assertEquals(activity, result.get());
        verify(activityRepository, times(1)).findById(anyInt());
    }

    @Test
    void getActivitiesByDate() {
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        when(activityRepository.findAllByDate(any(LocalDate.class))).thenReturn(Arrays.asList(activity1, activity2));

        List<Activity> result = activityService.getActivitiesByDate(LocalDate.now());

        assertEquals(2, result.size());
        verify(activityRepository, times(1)).findAllByDate(any(LocalDate.class));
    }
    @Test
    void getActivitiesByWeek(){
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        when(activityRepository.findAllByDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(Arrays.asList(activity1, activity2));

        List<Activity> result = activityService.getActivitiesByWeek(LocalDate.now());

        assertEquals(2, result.size());
        verify(activityRepository, times(1)).findAllByDateBetween(any(LocalDate.class), any(LocalDate.class));
    }
    @Test
    void getActivitiesByMonth(){
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        when(activityRepository.findAllByDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(Arrays.asList(activity1, activity2));

        List<Activity> result = activityService.getActivitiesByMonth(LocalDate.now());

        assertEquals(2, result.size());
        verify(activityRepository, times(1)).findAllByDateBetween(any(LocalDate.class), any(LocalDate.class));
    }
}
package kea.exam.xpbowlingbackend.activities;

import kea.exam.xpbowlingbackend.activity.ActivityService;
import kea.exam.xpbowlingbackend.activity.dtos.AllAvailableSlotsForDay;
import kea.exam.xpbowlingbackend.activity.dtos.AvailableRequestDTO;
import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.activity.entities.ActivityType;
import kea.exam.xpbowlingbackend.activity.repositories.ActivityRepository;
import kea.exam.xpbowlingbackend.reservation.competition.CompetitionDay;
import kea.exam.xpbowlingbackend.reservation.competition.CompetitionDayRepository;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ActivityServiceTest {

    @Mock
    ActivityRepository activityRepository;

    @Mock
    RecurringBowlingReservationRepository recurringBowlingReservationRepository;

    @Mock
    CompetitionDayRepository competitionDayRepository;

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

    @Test
    void getAvailableAtTime(){
        Activity activity1 = new Activity(LocalTime.of(11, 0), LocalTime.of(12, 0), LocalDate.of(2024, 2,3), ActivityType.AIRHOCKEY, 2);

        Activity activity2 = new Activity(LocalTime.of(11, 0), LocalTime.of(12, 0), LocalDate.of(2024, 2,3), ActivityType.AIRHOCKEY, 2);
        when(activityRepository.findAllByDateAndActivityType(any(LocalDate.class), any())).thenReturn(Arrays.asList(activity1, activity2));

        int result = activityService.getAvailableAtTime(new AvailableRequestDTO(LocalDate.of(2024, 2,3), LocalTime.of(10, 0), LocalTime.of(12, 0), ActivityType.AIRHOCKEY));

        assertEquals(2, result);

    }

    @Test
    public void getAllAvailabilitiesForDay() {

        competitionDayRepository.save(new CompetitionDay(LocalDate.of(2024, 1, 1)));
        AllAvailableSlotsForDay list = activityService.getAllAvailabilitiesForDay(LocalDate.of(2024, 1, 1));

    }

}
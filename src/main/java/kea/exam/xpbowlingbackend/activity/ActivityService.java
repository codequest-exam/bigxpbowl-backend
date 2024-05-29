package kea.exam.xpbowlingbackend.activity;

import kea.exam.xpbowlingbackend.activity.dtos.AllAvailableSlotsForDay;
import kea.exam.xpbowlingbackend.activity.dtos.AvailableRequestDTO;
import kea.exam.xpbowlingbackend.activity.dtos.AvailableResponseDTO;
import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.activity.entities.ActivityType;
import kea.exam.xpbowlingbackend.activity.repositories.ActivityRepository;
import kea.exam.xpbowlingbackend.activity.repositories.AirhockeyTableRepository;
import kea.exam.xpbowlingbackend.activity.repositories.BowlingLaneRepository;
import kea.exam.xpbowlingbackend.activity.repositories.DiningTableRepository;
import kea.exam.xpbowlingbackend.reservation.competition.CompetitionDayRepository;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservationRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final RecurringBowlingReservationRepository recurringBowlingReservationRepository;
    private final CompetitionDayRepository competitionDayRepository;

    private final BowlingLaneRepository bowlingLaneRepository;
    private final AirhockeyTableRepository airhockeyTableRepository;

    private final DiningTableRepository diningTableRepository;


    public ActivityService(BowlingLaneRepository bowlingLaneRepository, AirhockeyTableRepository airhockeyTableRepository, DiningTableRepository diningTableRepository ,ActivityRepository activityRepository, RecurringBowlingReservationRepository recurringBowlingReservationRepository, CompetitionDayRepository competitionDayRepository) {
        this.activityRepository = activityRepository;
        this.recurringBowlingReservationRepository = recurringBowlingReservationRepository;
        this.competitionDayRepository = competitionDayRepository;
        this.bowlingLaneRepository = bowlingLaneRepository;
        this.airhockeyTableRepository = airhockeyTableRepository;
        this.diningTableRepository = diningTableRepository;

    }


    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }


    public List<Activity> saveAll(List<Activity> activities) {
        return activityRepository.saveAll(activities);
    }

    public Optional<Activity> getActivityById(int id) {
        return activityRepository.findById(id);
    }


    public List<Activity> getActivitiesByDate(LocalDate date) {
        return activityRepository.findAllByDate(date);
    }

    public List<Activity> getActivitiesByWeek(LocalDate date) {
        return activityRepository.findAllByDateBetween(date, date.plusWeeks(1));
    }

    public List<Activity> getActivitiesByMonth(LocalDate date) {
        return activityRepository.findAllByDateBetween(date, date.plusMonths(1));
    }

    public void deleteAll(List<Activity> activities) {
        activityRepository.deleteAll(activities);
    }

    public void deleteActivity(int id) {
        activityRepository.deleteById(id);
    }

    public int getAvailableAtTime(AvailableRequestDTO req) {
        System.out.println("req: " + req);
        System.out.println("date: " + req.date());
        System.out.println("activityType: " + req.activityType());
        System.out.println("startTime: " + req.startTime());
        System.out.println("endTime: " + req.endTime());
        List<Activity> found = activityRepository.findAllByDateAndActivityType(req.date(), req.activityType());
        System.out.println(found);
        System.out.println("size of found: " + found.size());
        int count = 0;
        for (Activity activity : found) {
            System.out.println("endtime is after start time: " + activity.getEndTime().isAfter(req.startTime()));
            System.out.println("starttime is before end time: " + activity.getStartTime().isBefore(req.endTime()));
            if (activityOverlapsSecond(req, activity)) {
                System.out.println("COUNTING OVERLAP BY 1");
                count += activity.getAmountBooked();
            }
        }

        if (req.activityType().equals(ActivityType.BOWLING)) {
            DayOfWeek dayOfWeek = req.date().getDayOfWeek();
            List<RecurringBowlingReservation> recurringBowlingReservations = recurringBowlingReservationRepository.findAllByDayOfWeek(dayOfWeek);
            System.out.println("recurringBowlingReservations: " + recurringBowlingReservations.size());
            for (RecurringBowlingReservation recurringBowlingReservation : recurringBowlingReservations) {
                if (activityOverlapsSecond(req, recurringBowlingReservation)) {
                    System.out.println("overlapping recurring reservation");
                    count++;
                }
            }
            System.out.println("bowling");
            count = 20 - count;
        }
        if (req.activityType().equals(ActivityType.DINING)) {
            System.out.println("dining");
            count = 20 - count;
        }
        if (req.activityType().equals(ActivityType.AIRHOCKEY)) {
            System.out.println("airhockey");
            System.out.println("pre count: " + count);
            count = 6 - count;
            System.out.println("post count: " + count);
        }
        if (req.activityType().equals(ActivityType.CHILDBOWLING)) {
            count = 4 - count;
        }

        System.out.println("returning count: " + count);
        return count;
    }


    public AllAvailableSlotsForDay getAllAvailabilitiesForDay(LocalDate date) {
        boolean competitionCheck = competitionDayRepository.existsByDate(date);

        // Get seperate lists for each activity type
        List<Activity> bowling = activityRepository.findAllByDateAndActivityType(date, ActivityType.BOWLING);
        List<Activity> dining = activityRepository.findAllByDateAndActivityType(date, ActivityType.DINING);
        List<Activity> airHockey = activityRepository.findAllByDateAndActivityType(date, ActivityType.AIRHOCKEY);
        List<Activity> childBowling = activityRepository.findAllByDateAndActivityType(date, ActivityType.CHILDBOWLING);
        List<RecurringBowlingReservation> recurringBowlingReservations = recurringBowlingReservationRepository.findAllByDayOfWeek(date.getDayOfWeek());

        // Get capacity for each activity type
        int bowlingCapacity = bowlingLaneRepository.countByMaintenanceAndChildFriendly(false, false);
        int childBowlingCapacity = bowlingLaneRepository.countByMaintenanceAndChildFriendly(false, true);
        int diningCapacity = diningTableRepository.countByMaintenance(false);
        int airHockeyCapacity = airhockeyTableRepository.countByMaintenance(false);

        System.out.println("bowlingCapacity: " + bowlingCapacity);
        System.out.println("childBowlingCapacity: " + childBowlingCapacity);
        System.out.println("diningCapacity: " + diningCapacity);
        System.out.println("airHockeyCapacity: " + airHockeyCapacity);

        List<AvailableResponseDTO> bowlingResponse = getListOfTimes(competitionCheck, bowling, recurringBowlingReservations, bowlingCapacity);
        List<AvailableResponseDTO> diningResponse = getListOfTimes(dining, diningCapacity);
        List<AvailableResponseDTO> airHockeyResponse = getListOfTimes(airHockey, airHockeyCapacity);
        List<AvailableResponseDTO> childBowlingResponse = getListOfTimes(competitionCheck, childBowling, childBowlingCapacity);

        return new AllAvailableSlotsForDay(bowlingResponse, childBowlingResponse, diningResponse, airHockeyResponse);
    }

    public List<AvailableResponseDTO> getListOfTimes(List<Activity> incomingActivities, int capacity){
        return getListOfTimes(incomingActivities, null, false, capacity);
    }

    public List<AvailableResponseDTO> getListOfTimes(boolean competitionCheck, List<Activity> incomingActivities, int capacity)  {
        if (competitionCheck) {
            return getListOfTimes(null, null, true, capacity);
        }
        return getListOfTimes(incomingActivities, null, competitionCheck, capacity);
    }

    public List<AvailableResponseDTO> getListOfTimes(boolean competitionCheck, List<Activity> bowlingLanes, List<RecurringBowlingReservation> recurringBowlingLanes, int capacity ) {

        System.out.println("competitionCheck: " + competitionCheck);
        if (competitionCheck) {
            return getListOfTimes(null,null, true, capacity);
        }
        return getListOfTimes(bowlingLanes, recurringBowlingLanes, false, capacity);
    }

    private List<AvailableResponseDTO> getListOfTimes(List<Activity> incomingActivities, List<RecurringBowlingReservation> recurringBowlingReservations, boolean full, int capacity) {
        List<AvailableResponseDTO> availableResponseDTOList = new ArrayList<>();
        for (int i = 10; i < 24; i++) {
            LocalTime t = LocalTime.of(i, 0);
            if (full) {
                availableResponseDTOList.add(new AvailableResponseDTO(t, 0));
            }
            else {
                int count = 0;
                for (Activity activity : incomingActivities) {
                    if (overlaps(t, activity)) {
                        count += activity.getAmountBooked();
                    }
                }
                if (recurringBowlingReservations != null) {
                    for (RecurringBowlingReservation recurringBowlingReservation : recurringBowlingReservations) {
                        if (overlaps(t, recurringBowlingReservation)) {
                            count++;
                        }
                    }
                }
                int finalCount = capacity- count;
                availableResponseDTOList.add(new AvailableResponseDTO(t, finalCount));
            }
        }
        return availableResponseDTOList;
    }


    private boolean overlaps(LocalTime t, Activity activity) {
        return activity.getStartTime() == t || (activity.getEndTime().isAfter(t) && activity.getStartTime().isBefore(t));
    }
    private boolean overlaps(LocalTime t, RecurringBowlingReservation recurring) {
        return recurring.getStartTime() == t || (recurring.getEndTime().isAfter(t) && recurring.getStartTime().isBefore(t));
    }


    private Activity startTimeOverlaps(Activity activityToCheck, int hour) {
        if (activityToCheck.getStartTime().getHour() == hour) {
            return activityToCheck;
        }
        return null;
    }

    public boolean activityOverlapsSecond(AvailableRequestDTO activityToCheck, Activity activity) {
        boolean check = (activity.getEndTime().isAfter(activityToCheck.startTime()) && activity.getStartTime().isBefore(activityToCheck.endTime()));
        System.out.println("check: " + check);
        return check;
        //return (activity.getEndTime().isAfter(activityToCheck.startTime()) && activity.getStartTime().compareTo(activityToCheck.endTime()) <= 0);
    }

    public boolean activityOverlapsSecond(AvailableRequestDTO activityToCheck, RecurringBowlingReservation recurring) {
        return (recurring.getEndTime().isAfter(activityToCheck.startTime()) && recurring.getStartTime().isBefore(activityToCheck.endTime()));
        //return (recurring.getEndTime().isAfter(activityToCheck.startTime()) && recurring.getStartTime().compareTo(activityToCheck.endTime()) <= 0);
    }
}

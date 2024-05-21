package kea.exam.xpbowlingbackend.config;

import kea.exam.xpbowlingbackend.activity.entities.*;
import kea.exam.xpbowlingbackend.activity.repositories.*;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservationRepository;
import kea.exam.xpbowlingbackend.reservation.Reservation;
import kea.exam.xpbowlingbackend.reservation.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    private final BowlingLaneRepository bowlingLaneRepository;
    private final AirhockeyTableRepository airhockeyTableRepository;
    //private final ActivityTypeRepository activityTypeRepository;
    private final ActivityRepository activityRepository;
    private final ReservationRepository reservationRepository;
    private final DiningTableRepository diningTableRepository;
    private final RecurringBowlingReservationRepository recurringBowlingReservationRepository;
    private final CompetitionDayRepository competitionDayRepository;

//    private ActivityType bowling = ActivityType.BOWLING;
//    private ActivityType airhockey = ActivityType.AIRHOCKEY;
//    private ActivityType dining = ActivityType.DINING;

    private final List<BowlingLane> bowlingLanes = new ArrayList<>();
    private final List<AirhockeyTable> airhockeyTables = new ArrayList<>();
    private final List<DiningTable> diningTables = new ArrayList<>();
    private final List<Activity> bowlingActivities = new ArrayList<>();
    private final List<Activity> airhockeyActivities = new ArrayList<>();
    private final List<Activity> diningActivities = new ArrayList<>();


    public InitData(CompetitionDayRepository competitionDayRepository, RecurringBowlingReservationRepository recurringBowlingReservationRepository, ReservationRepository reservationRepository, BowlingLaneRepository bowlingLaneRepository, AirhockeyTableRepository airhockeyTableRepository, ActivityRepository activityRepository, DiningTableRepository diningTableRepository) {
        this.reservationRepository = reservationRepository;
        this.bowlingLaneRepository = bowlingLaneRepository;
        //this.activityTypeRepository = activityTypeRepository;
        this.airhockeyTableRepository = airhockeyTableRepository;
        this.activityRepository = activityRepository;
        this.diningTableRepository = diningTableRepository;
        this.recurringBowlingReservationRepository = recurringBowlingReservationRepository;
        this.competitionDayRepository = competitionDayRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO: Add init data to all repositories
        initTypes();
        initDiningTables();
        initAirhockeyTables();
        initBowlingLanes();
        initCompetitionDays();
        initRecurringReservations();
       initActivities();
        initReservations();
    }

    private void initCompetitionDays() {
        List<CompetitionDay> tempDays = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tempDays.add(new CompetitionDay(LocalDate.of(2024, 5 + i, 25 + j)));
            }
        }
        competitionDayRepository.saveAll(tempDays);
    }

    private void initRecurringReservations() {
        List<RecurringBowlingReservation> tempReservations = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            for (DayOfWeek day : DayOfWeek.values()) {
                if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                    continue;
                }
                tempReservations.add(new RecurringBowlingReservation(LocalTime.of(10, 0), LocalTime.of(17, 0), day, "12345678", "John Doe", 4));
            }
        }
        recurringBowlingReservationRepository.saveAll(tempReservations);
    }

    private void initTypes() {
//        bowling = activityTypeRepository.save(new ActivityType("bowling"));
//        airhockey = activityTypeRepository.save(new ActivityType("airhockey"));
//        dining = activityTypeRepository.save(new ActivityType("dining"));
    }

    private void initBowlingLanes() {
        List<BowlingLane> tempLanes = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            tempLanes.add(new BowlingLane(false, i > 19, i + 1));
        }
        bowlingLanes.addAll(bowlingLaneRepository.saveAll(tempLanes));
    }

    private void initAirhockeyTables() {
        List<AirhockeyTable> tempTables = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            tempTables.add(new AirhockeyTable(false, i + 1));
        }
        airhockeyTables.addAll(airhockeyTableRepository.saveAll(tempTables));
    }

    private void initDiningTables() {
        List<DiningTable> tempTables = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tempTables.add(new DiningTable(false, i + 1));
        }
        diningTables.addAll(diningTableRepository.saveAll(tempTables));
    }
    public void initActivities() {
        List<Activity> activities = new ArrayList<>();

        // Define fixed hourly intervals
        List<LocalTime> startTimes = List.of(
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0),
                LocalTime.of(16, 0),
                LocalTime.of(17, 0),
                LocalTime.of(18, 0),
                LocalTime.of(19, 0),
                LocalTime.of(20, 0)
        );

        LocalDate currentDate = LocalDate.now();
        for (LocalTime startTime : startTimes) {
            Activity activity = new Activity();
            activity.setStartTime(startTime);
            activity.setEndTime(startTime.plusHours(2));
            activity.setDate(currentDate);
            activity.setActivityType(ActivityType.values()[(int) (Math.random() * ActivityType.values().length)]); // Random activity type
            activity.setAmountBooked(1);
            activities.add(activity);
        }
        activityRepository.saveAll(activities);
    }
    private void initReservations() {
        List<Reservation> tempReservations = new ArrayList<>();

        for (int i = 0; i < bowlingActivities.size(); i++) {
            if (airhockeyActivities.size() > i && diningActivities.size() > i) {
                tempReservations.add(new Reservation("12345678", "John Doe", 4, List.of(bowlingActivities.get(i), airhockeyActivities.get(i), diningActivities.get(i))));
            } else if (diningActivities.size() > i) {
                tempReservations.add(new Reservation("12345678", "John Doe", 4, List.of(bowlingActivities.get(i), diningActivities.get(i))));
            } else {
                tempReservations.add(new Reservation("12345678", "John Doe", 4, List.of(bowlingActivities.get(i))));
            }
        }

        reservationRepository.saveAll(tempReservations);
    }


}

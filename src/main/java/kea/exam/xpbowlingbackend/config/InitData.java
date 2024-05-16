package kea.exam.xpbowlingbackend.config;

import kea.exam.xpbowlingbackend.activity.entities.*;
import kea.exam.xpbowlingbackend.activity.repositories.*;
import kea.exam.xpbowlingbackend.reservation.RecurringBowlingReservation;
import kea.exam.xpbowlingbackend.reservation.RecurringBowlingReservationRepository;
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
    private final ActivityTypeRepository activityTypeRepository;
    private final ActivityRepository activityRepository;
    private final ReservationRepository reservationRepository;
    private final DiningTableRepository diningTableRepository;
    
    private final RecurringBowlingReservationRepository recurringBowlingReservationRepository;

    private final CompetitionDayRepository competitionDayRepository;
    private ActivityType bowling;
    private ActivityType airhockey;
    private ActivityType dining;
    private  List<BowlingLane> bowlingLanes = new ArrayList<>();
    private final List<AirhockeyTable> airhockeyTables = new ArrayList<>();
    private final List<DiningTable> diningTables = new ArrayList<>();
    private final List<Activity> bowlingActivities = new ArrayList<>();
    private final List<Activity> airhockeyActivities = new ArrayList<>();
    private final List<Activity> diningActivities = new ArrayList<>();



    public InitData(CompetitionDayRepository competitionDayRepository, RecurringBowlingReservationRepository recurringBowlingReservationRepository, ReservationRepository reservationRepository, BowlingLaneRepository bowlingLaneRepository, ActivityTypeRepository activityTypeRepository, AirhockeyTableRepository airhockeyTableRepository, ActivityRepository activityRepository, DiningTableRepository diningTableRepository) {
        this.reservationRepository = reservationRepository;
        this.bowlingLaneRepository = bowlingLaneRepository;
        this.activityTypeRepository = activityTypeRepository;
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
                tempReservations.add(new RecurringBowlingReservation(bowlingLanes.get(i), LocalTime.of(10, 0), LocalTime.of(17, 0), day, "12345678", "John Doe", 4));
            }
        }
        recurringBowlingReservationRepository.saveAll(tempReservations);
    }

    private void initTypes() {
        bowling = activityTypeRepository.save(new ActivityType("bowling"));
        airhockey = activityTypeRepository.save(new ActivityType("airhockey"));
        dining = activityTypeRepository.save(new ActivityType("dining"));
    }

    private void initBowlingLanes() {
        List<BowlingLane> tempLanes = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            tempLanes.add(new BowlingLane(bowling, false, i > 19, false, i + 1));
        }
        bowlingLanes.addAll(bowlingLaneRepository.saveAll(tempLanes));
    }

    private void initAirhockeyTables() {
        List<AirhockeyTable> tempTables = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            tempTables.add(new AirhockeyTable(airhockey, false, i + 1));
        }
        airhockeyTables.addAll(airhockeyTableRepository.saveAll(tempTables));
    }

    private void initDiningTables() {
        List<DiningTable> tempTables = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tempTables.add(new DiningTable(dining, false, i + 1));
        }
        diningTables.addAll(diningTableRepository.saveAll(tempTables));
    }

    private void initReservations() {
        List<Reservation> tempReservations = new ArrayList<>();

        for (int i = 0; i < bowlingActivities.size(); i++) {
            if (airhockeyActivities.size() > i && diningActivities.size() > i) {
                tempReservations.add(new Reservation("12345678", "John Doe", 4, List.of(bowlingActivities.get(i), airhockeyActivities.get(i), diningActivities.get(i))));
            } else if (diningActivities.size() > i) {
                tempReservations.add(new Reservation("12345678", "John Doe", 4, List.of(bowlingActivities.get(i), diningActivities.get(i))));
            } else if (bowlingActivities.size() > i) {
                tempReservations.add(new Reservation("12345678", "John Doe", 4, List.of(bowlingActivities.get(i))));
            }
        }
        reservationRepository.saveAll(tempReservations);
    }

    private void initActivities() {
        List<Activity> tempBowling = new ArrayList<>();
        List<Activity> tempAirhockey = new ArrayList<>();
        List<Activity> tempDining = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 6; j++) {
                tempBowling.add(new Activity(bowling, LocalTime.of(9 + j, 0), LocalTime.of(10 + j, 0), LocalDate.of(2024, 5,1 + i), List.of(bowlingLanes.get(i))));
            }
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                tempAirhockey.add(new Activity(airhockey, LocalTime.of(9 + j, 0), LocalTime.of(10 + j, 0), LocalDate.of(2024, 5,1 + i), List.of(airhockeyTables.get(i))));
            }
        }

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 6; j++) {
                tempDining.add(new Activity(dining, LocalTime.of(9 + j, 0), LocalTime.of(10 + j, 0), LocalDate.of(2024, 5,1 + i), List.of(diningTables.get(i))));
            }
        }
        bowlingActivities.addAll(activityRepository.saveAll(tempBowling));
        airhockeyActivities.addAll(activityRepository.saveAll(tempAirhockey));
        diningActivities.addAll(activityRepository.saveAll(tempDining));
    }
}

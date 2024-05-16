package kea.exam.xpbowlingbackend.config;

import kea.exam.xpbowlingbackend.activity.entities.*;
import kea.exam.xpbowlingbackend.activity.repositories.*;
import kea.exam.xpbowlingbackend.reservation.Reservation;
import kea.exam.xpbowlingbackend.reservation.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
    private ActivityType bowling;
    private ActivityType airhockey;
    private ActivityType dining;
    private  List<BowlingLane> bowlingLanes = new ArrayList<>();
    private final List<AirhockeyTable> airhockeyTables = new ArrayList<>();
    private final List<DiningTable> diningTables = new ArrayList<>();
    private final List<Activity> activities = new ArrayList<>();

    public InitData(ReservationRepository reservationRepository, BowlingLaneRepository bowlingLaneRepository, ActivityTypeRepository activityTypeRepository, AirhockeyTableRepository airhockeyTableRepository, ActivityRepository activityRepository, DiningTableRepository diningTableRepository) {
        this.reservationRepository = reservationRepository;
        this.bowlingLaneRepository = bowlingLaneRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.airhockeyTableRepository = airhockeyTableRepository;
        this.activityRepository = activityRepository;
        this.diningTableRepository = diningTableRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initTypes();
        initDiningTables();
        initAirhockeyTables();
        initBowlingLanes();
        initActivities();
        initReservations();
    }

    private void initTypes() {
        bowling = activityTypeRepository.save(new ActivityType("bowling"));
        airhockey = activityTypeRepository.save(new ActivityType("airhockey"));
        dining = activityTypeRepository.save(new ActivityType("dining"));
    }

    private void initBowlingLanes() {
        List<BowlingLane> tempLanes = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            tempLanes.add(new BowlingLane(bowling, false, i < 4, false, i + 1));
        }
      //bowlingLanes = new ArrayList<>(bowlingLaneRepository.saveAll(bowlingLanes)) ;
        bowlingLanes.addAll(bowlingLaneRepository.saveAll(tempLanes));

    }

    private void initAirhockeyTables() {
        AirhockeyTable airhockeyTable = airhockeyTableRepository.save(new AirhockeyTable(airhockey, false, 1));

        var tables = airhockeyTableRepository.findAll();
        for (AirhockeyTable table : tables) {
            System.out.println(table.getId());
        }
    }

    private void initDiningTables() {
        DiningTable diningTable = diningTableRepository.save(new DiningTable(dining, false, 1));
    }


    private void initReservations() {
        reservationRepository.save(new Reservation("12345678", "John Doe", 4, null)) ;
    }

    private void initActivities() {

        activityRepository.save(new Activity(bowling, "14:00", "15:00", LocalDate.of(2024, 5,16), List.of(bowlingLanes.get(0), bowlingLanes.get(1))));
    }
}

package kea.exam.xpbowlingbackend.config;

import kea.exam.xpbowlingbackend.activity.entities.*;
import kea.exam.xpbowlingbackend.activity.repositories.*;
import kea.exam.xpbowlingbackend.reservation.competition.CompetitionDay;
import kea.exam.xpbowlingbackend.reservation.competition.CompetitionDayRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class InitData implements CommandLineRunner {
    private final ActivityRepository activityRepository;
    private final ReservationRepository reservationRepository;
    private final RecurringBowlingReservationRepository recurringBowlingReservationRepository;
    private final CompetitionDayRepository competitionDayRepository;

    public InitData(CompetitionDayRepository competitionDayRepository, RecurringBowlingReservationRepository recurringBowlingReservationRepository, ReservationRepository reservationRepository, BowlingLaneRepository bowlingLaneRepository, AirhockeyTableRepository airhockeyTableRepository, ActivityRepository activityRepository, DiningTableRepository diningTableRepository) {
        this.reservationRepository = reservationRepository;
        this.activityRepository = activityRepository;
        this.recurringBowlingReservationRepository = recurringBowlingReservationRepository;
        this.competitionDayRepository = competitionDayRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO: Add init data to all repositories
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
    public void initActivities() {
        List<Activity> activities = Arrays.asList(
                new Activity(LocalTime.of(10, 0), LocalTime.of(11, 0), LocalDate.now(), ActivityType.BOWLING, 1),
                new Activity(LocalTime.of(11, 0), LocalTime.of(13, 0), LocalDate.now(), ActivityType.DINING, 1),
                new Activity(LocalTime.of(13, 0), LocalTime.of(15, 0), LocalDate.now(), ActivityType.AIRHOCKEY, 1),
                new Activity(LocalTime.of(15, 0), LocalTime.of(17, 0), LocalDate.now(), ActivityType.BOWLING, 1),
                new Activity(LocalTime.of(17, 0), LocalTime.of(19, 0), LocalDate.now(), ActivityType.DINING, 1),
                new Activity(LocalTime.of(19, 0), LocalTime.of(21, 0), LocalDate.now(), ActivityType.AIRHOCKEY, 1)
        );

        activityRepository.saveAll(activities);
    }
    public void initReservations() {
        List<Activity> activities = activityRepository.findAll();
        Random random = new Random();

        Reservation reservation1 = new Reservation();
        reservation1.setPhoneNumber("123-456-7891");
        reservation1.setName("Alice Johnson");
        reservation1.setParticipants(random.nextInt(5) + 1);
        reservation1.setActivities(activities.subList(0, 2));

        Reservation reservation2 = new Reservation();
        reservation2.setPhoneNumber("123-456-7892");
        reservation2.setName("Bob Smith");
        reservation2.setParticipants(random.nextInt(5) + 1);
        reservation2.setActivities(activities.subList(2, 4));

        Reservation reservation3 = new Reservation();
        reservation3.setPhoneNumber("123-456-7893");
        reservation3.setName("Charlie Brown");
        reservation3.setParticipants(random.nextInt(5) + 1);
        reservation3.setActivities(activities.subList(4, 6));


        List<Reservation> reservations = Arrays.asList(reservation1, reservation2, reservation3);
        reservationRepository.saveAll(reservations);
    }
}





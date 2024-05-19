package kea.exam.xpbowlingbackend.reservation;


import kea.exam.xpbowlingbackend.activity.ActivityService;
import kea.exam.xpbowlingbackend.activity.dtos.ActivityResponseDto;
import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    ReservationRepository reservationRepository;
    ActivityService activityService;

    RecurringBowlingReservationRepository recurringBowlingReservationRepository;

    public ReservationService(ReservationRepository reservationRepository, ActivityService activityService, RecurringBowlingReservationRepository recurringBowlingReservationRepository) {
        this.reservationRepository = reservationRepository;
        this.activityService = activityService;
        this.recurringBowlingReservationRepository = recurringBowlingReservationRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional< Reservation> getReservationById(int id) {
        return reservationRepository.findById(id);
    }

    public Reservation createReservation(Reservation reservation) {
        System.out.println("saving reservations");
        List<Activity> activities = reservation.getActivities();
        List<Activity> savedActivities = activityService.saveAll(activities);

        System.out.println("SAVED ACTIVITIES");
        System.out.println(savedActivities);

//        List<Activity> savedActivities = new ArrayList<>();
//        for (Activity activity : activities) {
//            System.out.println(activity.getActivityType());
//        }
        reservation.setActivities(savedActivities);
        return reservationRepository.save(reservation);
    }

    public RecurringBowlingReservation createRecurringReservation(RecurringBowlingReservation recurringBowlingReservation) {
        return recurringBowlingReservationRepository.save(recurringBowlingReservation);
    }

    public Optional< Reservation> updateReservation(int id, Reservation reservation) {
        Optional<Reservation> opt = reservationRepository.findById(id);
        if (opt.isPresent()) {
            reservation.setId(id);
            return Optional.of(reservationRepository.save(reservation)) ;
        }
        else {
            return Optional.empty();
        }
    }

    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }


    public List<ActivityResponseDto> toActivityResponseDto(List<Activity> activities) {
        return activities.stream()
                .map(this::toActivityResponseDto)
                .toList();
    }

    private ActivityResponseDto toActivityResponseDto(Activity activity) {
        ActivityResponseDto dto = new ActivityResponseDto();
        dto.setId(activity.getId());
        dto.setStartTime(activity.getStartTime());
        dto.setEndTime(activity.getEndTime());
        dto.setDate(activity.getDate());
        if (activity.getAirhockeyTables() != null) {
            dto.setAirhockeyTables(activity.getAirhockeyTables());
        }
        if (activity.getBowlingLanes() != null) {
            dto.setBowlingLanes(activity.getBowlingLanes());
        }
        if (activity.getDiningTables() != null) {
            dto.setDiningTables(activity.getDiningTables());
        }
        return dto;
    }
}

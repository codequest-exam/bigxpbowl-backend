package kea.exam.xpbowlingbackend.reservation;


import kea.exam.xpbowlingbackend.activity.ActivityService;
import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        List<Activity> activities = reservation.getActivities();
        List<Activity> savedActivities = activityService.saveAll(activities);
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
}

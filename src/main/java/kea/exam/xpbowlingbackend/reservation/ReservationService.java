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

    public Optional<Reservation> getReservationById(int id) {
        return reservationRepository.findById(id);
    }


    public Reservation createReservation(Reservation reservation) {
        List<Activity> activities = reservation.getActivities();
        if (activities != null && !activities.isEmpty()) {
            activityService.saveAll(activities);
        }
        return reservationRepository.save(reservation);
    }    public RecurringBowlingReservation createRecurringReservation(RecurringBowlingReservation recurringBowlingReservation) {
        return recurringBowlingReservationRepository.save(recurringBowlingReservation);
    }
    public Reservation updateReservationGeneral(int id, Reservation reservation) {
        List<Activity> activities = reservation.getActivities();
        if (activities != null && !activities.isEmpty()) {
            activityService.saveAll(activities);
        }
        return reservationRepository.save(reservation);
    }
    public void deleteReservation(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        List<Activity> activities = reservation.getActivities();
        activities.size(); // This line ensures that the activities are fetched while the Session is still open
        reservationRepository.deleteById(reservationId);
        if (!activities.isEmpty()) {
            activityService.deleteAll(activities);
        }
    }
    }



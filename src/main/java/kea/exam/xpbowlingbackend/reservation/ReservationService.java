package kea.exam.xpbowlingbackend.reservation;


import kea.exam.xpbowlingbackend.activity.ActivityService;
import kea.exam.xpbowlingbackend.activity.dtos.ActivityResponseDTO;
import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.reservation.competition.CompetitionDay;
import kea.exam.xpbowlingbackend.reservation.competition.CompetitionDayRepository;
import kea.exam.xpbowlingbackend.reservation.dtos.DTOConverter;
import kea.exam.xpbowlingbackend.reservation.dtos.ReservationResponseDTO;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    ReservationRepository reservationRepository;
    ActivityService activityService;

    RecurringBowlingReservationRepository recurringBowlingReservationRepository;
    CompetitionDayRepository competitionDayRepository;

    public ReservationService(ReservationRepository reservationRepository, ActivityService activityService, RecurringBowlingReservationRepository recurringBowlingReservationRepository, CompetitionDayRepository competitionDayRepository) {
        this.reservationRepository = reservationRepository;
        this.activityService = activityService;
        this.recurringBowlingReservationRepository = recurringBowlingReservationRepository;
        this.competitionDayRepository = competitionDayRepository;
    }

    public List<ReservationResponseDTO> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(DTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<RecurringBowlingReservation> getAllRecurringReservations() {
        return recurringBowlingReservationRepository.findAll();
    }
    public List<CompetitionDay> getAllCompetitionDays(){
        return competitionDayRepository.findAll();
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
        activities.size();
        reservationRepository.deleteById(reservationId);
        if (!activities.isEmpty()) {
            activityService.deleteAll(activities);
        }
    }

    }



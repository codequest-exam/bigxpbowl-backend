package kea.exam.xpbowlingbackend.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import kea.exam.xpbowlingbackend.activity.ActivityService;
import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.reservation.ReservationRepository;
import kea.exam.xpbowlingbackend.reservation.dtos.DTOConverter;
import kea.exam.xpbowlingbackend.reservation.dtos.ReservationResponseDTO;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ActivityService activityService;

    @Mock
    private RecurringBowlingReservationRepository recurringBowlingReservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllReservationsReturnsAllReservations() {
        // Arrange
        Reservation reservation1 = new Reservation();
        reservation1.setActivities(new ArrayList<>());  // Ensure non-null activities list
        Reservation reservation2 = new Reservation();
        reservation2.setActivities(new ArrayList<>());  // Ensure non-null activities list
        List<Reservation> mockReservations = Arrays.asList(reservation1, reservation2);
        when(reservationRepository.findAll()).thenReturn(mockReservations);

        // Act
        List<ReservationResponseDTO> reservations = reservationService.getAllReservations();

        // Assert
        assertEquals(2, reservations.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void getReservationByIdReturnsReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));

        // Act
        Optional<Reservation> result = reservationService.getReservationById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(reservation, result.get());
        verify(reservationRepository, times(1)).findById(1);
    }

    @Test
    void createReservationSavesReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        Activity activity = new Activity();
        reservation.setActivities(Arrays.asList(activity));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // Act
        Reservation result = reservationService.createReservation(reservation);

        // Assert
        assertEquals(reservation, result);
        verify(activityService, times(1)).saveAll(reservation.getActivities());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void createRecurringReservationSavesRecurringReservation() {
        // Arrange
        RecurringBowlingReservation recurringReservation = new RecurringBowlingReservation();
        when(recurringBowlingReservationRepository.save(any(RecurringBowlingReservation.class))).thenReturn(recurringReservation);

        // Act
        RecurringBowlingReservation result = reservationService.createRecurringReservation(recurringReservation);

        // Assert
        assertEquals(recurringReservation, result);
        verify(recurringBowlingReservationRepository, times(1)).save(recurringReservation);
    }

    @Test
    void updateReservationGeneralUpdatesReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        Activity activity = new Activity();
        reservation.setActivities(Arrays.asList(activity));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // Act
        Reservation result = reservationService.updateReservationGeneral(1, reservation);

        // Assert
        assertEquals(reservation, result);
        verify(activityService, times(1)).saveAll(reservation.getActivities());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void deleteReservationDeletesReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        Activity activity = new Activity();
        reservation.setActivities(Arrays.asList(activity));
        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));

        // Act
        reservationService.deleteReservation(1);

        // Assert
        verify(reservationRepository, times(1)).deleteById(1);
        verify(activityService, times(1)).deleteAll(reservation.getActivities());
    }
}

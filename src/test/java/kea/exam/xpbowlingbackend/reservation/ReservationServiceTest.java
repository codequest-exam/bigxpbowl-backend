package kea.exam.xpbowlingbackend.reservation;

import kea.exam.xpbowlingbackend.activity.ActivityService;
import kea.exam.xpbowlingbackend.activity.entities.Activity;

import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
        reservationService = new ReservationService(reservationRepository, activityService, recurringBowlingReservationRepository);
    }


    @Test
    void getAllReservationsReturnsAllReservations() {
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation1, reservation2));

        List<Reservation> reservations = reservationService.getAllReservations();

        assertEquals(2, reservations.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void getReservationByIdReturnsReservationWhenExists() {
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));

        Optional<Reservation> returnedReservation = reservationService.getReservationById(1);

        assertTrue(returnedReservation.isPresent());
        assertEquals(reservation, returnedReservation.get());
        verify(reservationRepository, times(1)).findById(1);
    }

    @Test
    void getReservationByIdReturnsEmptyWhenDoesNotExist() {
        when(reservationRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Reservation> returnedReservation = reservationService.getReservationById(1);

        assertFalse(returnedReservation.isPresent());
        verify(reservationRepository, times(1)).findById(1);
    }

    @Test
    void createReservationSavesAndReturnsReservation() {


        Reservation reservation = new Reservation("1234", "name", 4, List.of(
                new Activity( LocalTime.of(12, 0), LocalTime.of(13, 0), LocalDate.of(2021, 12, 24),List.of(
                        new BowlingLane(false, true, 25)), null, null
                )

        ));
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation returnedReservation = reservationService.createReservation(reservation);

        assertEquals(reservation, returnedReservation);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void updateReservationReturnsUpdatedReservationWhenExists() {
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Optional<Reservation> returnedReservation = reservationService.updateReservation(1, reservation);

        assertTrue(returnedReservation.isPresent());
        assertEquals(reservation, returnedReservation.get());
        verify(reservationRepository, times(1)).findById(1);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void updateReservationReturnsEmptyWhenDoesNotExist() {
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Reservation> returnedReservation = reservationService.updateReservation(1, reservation);

        assertFalse(returnedReservation.isPresent());
        verify(reservationRepository, times(1)).findById(1);
    }

    @Test
    void deleteReservationCallsDeleteById() {
        reservationService.deleteReservation(1);

        verify(reservationRepository, times(1)).deleteById(1);
    }
}
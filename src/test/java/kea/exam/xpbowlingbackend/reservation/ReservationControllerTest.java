package kea.exam.xpbowlingbackend.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationController = new ReservationController(reservationService);
    }

    @Test
    void getAllReservationsReturnsAllReservations() {
        // Arrange
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        when(reservationService.getAllReservations()).thenReturn(Arrays.asList(reservation1, reservation2));

        // Act
        List<Reservation> reservations = reservationController.getAllReservations();

        // Assert
        assertEquals(2, reservations.size());
        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    void getReservationByIdReturnsReservationWhenExists() {
        // Arrange
        Reservation reservation = new Reservation();
        when(reservationService.getReservationById(1)).thenReturn(Optional.of(reservation));

        // Act
        ResponseEntity<Reservation> response = reservationController.getReservationById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservation, response.getBody());
        verify(reservationService, times(1)).getReservationById(1);
    }

    @Test
    void getReservationByIdReturnsNotFoundWhenDoesNotExist() {
          // Arrange
            when(reservationService.getReservationById(1)).thenReturn(Optional.empty());

            // Act
            try {
                reservationController.getReservationById(1);
                fail("Expected ResponseStatusException");
            } catch (Exception e) {
                // Assert
                assertEquals("404 NOT_FOUND", e.getMessage());
                verify(reservationService, times(1)).getReservationById(1);
            }
    }
}
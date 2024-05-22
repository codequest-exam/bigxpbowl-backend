package kea.exam.xpbowlingbackend.reservation;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import kea.exam.xpbowlingbackend.reservation.dtos.ReservationResponseDTO;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllReservationsReturnsAllReservations() throws Exception {
        ReservationResponseDTO reservation1 = new ReservationResponseDTO(1, "Reservation 1", "123456789", 10, LocalDate.now(), List.of("Activity 1", "Activity 2"));
        ReservationResponseDTO reservation2 = new ReservationResponseDTO(2, "Reservation 2", "987654321", 5, LocalDate.now(), List.of("Activity 3"));
        when(reservationService.getAllReservations()).thenReturn(Arrays.asList(reservation1, reservation2));

        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Reservation 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Reservation 2"));

        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    void getReservationByIdReturnsReservation() throws Exception {

        Reservation reservation = new Reservation();
        reservation.setName("Test Reservation");
        when(reservationService.getReservationById(1)).thenReturn(Optional.of(reservation));


        mockMvc.perform(get("/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Reservation"));

        verify(reservationService, times(1)).getReservationById(1);
    }

    @Test
    void getReservationByIdReturnsNotFound() throws Exception {

        when(reservationService.getReservationById(1)).thenReturn(Optional.empty());


        mockMvc.perform(get("/reservations/1"))
                .andExpect(status().isNotFound());

        verify(reservationService, times(1)).getReservationById(1);
    }

    @Test
    void createStandardReservationCreatesReservation() throws Exception {

        Reservation reservation = new Reservation();
        reservation.setName("Test Reservation");
        when(reservationService.createReservation(any(Reservation.class))).thenReturn(reservation);


        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Reservation"));

        verify(reservationService, times(1)).createReservation(any(Reservation.class));
    }

    @Test
    void createRecurringReservationCreatesReservation() throws Exception {

        RecurringBowlingReservation recurringReservation = new RecurringBowlingReservation();
        when(reservationService.createRecurringReservation(any(RecurringBowlingReservation.class))).thenReturn(recurringReservation);


        mockMvc.perform(post("/reservations/recurring")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recurringReservation)))
                .andExpect(status().isOk());

        verify(reservationService, times(1)).createRecurringReservation(any(RecurringBowlingReservation.class));
    }

    @Test
    void updateStandardReservationUpdatesReservation() throws Exception {

        Reservation reservation = new Reservation();
        reservation.setName("Updated Reservation");
        when(reservationService.updateReservationGeneral(anyInt(), any(Reservation.class))).thenReturn(reservation);


        mockMvc.perform(put("/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Reservation"));

        verify(reservationService, times(1)).updateReservationGeneral(anyInt(), any(Reservation.class));
    }

    @Test
    void deleteReservationDeletesReservation() throws Exception {

        mockMvc.perform(delete("/reservations/1"))
                .andExpect(status().isOk());

        verify(reservationService, times(1)).deleteReservation(1);
    }
}

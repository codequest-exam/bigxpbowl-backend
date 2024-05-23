package kea.exam.xpbowlingbackend.reservation;

import kea.exam.xpbowlingbackend.reservation.dtos.ReservationResponseDTO;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReservationControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllReservationsReturnsAllReservations() {
        ResponseEntity<ReservationResponseDTO[]> response = restTemplate.getForEntity("/reservations", ReservationResponseDTO[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getReservationByIdReturnsReservation() {
        ResponseEntity<Reservation> response = restTemplate.getForEntity("/reservations/71", Reservation.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void createStandardReservationCreatesReservation() {
        Reservation reservation = new Reservation();
        reservation.setName("Test Reservation");

        ResponseEntity<Reservation> response = restTemplate.postForEntity("/reservations", reservation, Reservation.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void createRecurringReservationCreatesReservation() {
        RecurringBowlingReservation recurringReservation = new RecurringBowlingReservation();

        ResponseEntity<RecurringBowlingReservation> response = restTemplate.postForEntity("/reservations/recurring", recurringReservation, RecurringBowlingReservation.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void updateStandardReservationUpdatesReservation() {
        ResponseEntity<Reservation> getResponse = restTemplate.getForEntity("/reservations/72", Reservation.class);
        Reservation reservation = getResponse.getBody();

        reservation.setName("Updated Reservation");
        restTemplate.put("/reservations/72", reservation, Reservation.class);

        ResponseEntity<Reservation> updatedResponse = restTemplate.getForEntity("/reservations/72", Reservation.class);

        assertThat(updatedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedResponse.getBody()).isNotNull();
        assertThat(updatedResponse.getBody().getName()).isEqualTo("Updated Reservation");
    }

    @Test
    public void deleteReservationDeletesReservation() {
        restTemplate.delete("/reservations/71");

        ResponseEntity<Reservation> response = restTemplate.getForEntity("/reservations/71", Reservation.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
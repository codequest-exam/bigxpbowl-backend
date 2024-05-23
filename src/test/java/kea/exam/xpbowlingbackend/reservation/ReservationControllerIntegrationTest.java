package kea.exam.xpbowlingbackend.reservation;

import kea.exam.xpbowlingbackend.reservation.dtos.ReservationResponseDTO;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllReservationsReturnsAllReservations() {
        ResponseEntity<ReservationResponseDTO[]> response = restTemplate.getForEntity("/reservations", ReservationResponseDTO[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void getReservationByIdReturnsReservation() {
        // First create a reservation to ensure it exists
        Reservation reservation = new Reservation();
        reservation.setName("Test Reservation");
        ResponseEntity<Reservation> postResponse = restTemplate.postForEntity("/reservations", reservation, Reservation.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();

        int reservationId = postResponse.getBody().getId();

        ResponseEntity<Reservation> response = restTemplate.getForEntity("/reservations/" + reservationId, Reservation.class);

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

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); // Should be CREATED
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void updateStandardReservationUpdatesReservation() {
        // First create a reservation to ensure it exists
        Reservation reservation = new Reservation();
        reservation.setName("Test Reservation");
        ResponseEntity<Reservation> postResponse = restTemplate.postForEntity("/reservations", reservation, Reservation.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();

        int reservationId = postResponse.getBody().getId();

        // Debugging: Log the ID and initial name
        System.out.println("Created Reservation ID: " + reservationId);
        System.out.println("Initial Name: " + postResponse.getBody().getName());

        // Update the reservation
        Reservation updatedReservation = new Reservation();
        updatedReservation.setId(reservationId); // Set the ID explicitly
        updatedReservation.setName("Updated Reservation");
        restTemplate.put("/reservations/" + reservationId, updatedReservation);

        // Debugging: Fetch the reservation again after update
        ResponseEntity<Reservation> fetchResponse = restTemplate.getForEntity("/reservations/" + reservationId, Reservation.class);
        System.out.println("Fetched after update - Name: " + fetchResponse.getBody().getName());

        // Get the updated reservation
        ResponseEntity<Reservation> updatedResponse = restTemplate.getForEntity("/reservations/" + reservationId, Reservation.class);

        assertThat(updatedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedResponse.getBody()).isNotNull();
        assertThat(updatedResponse.getBody().getName()).isEqualTo("Updated Reservation");
    }


    @Test
    public void deleteReservationDeletesReservation() {
        // First create a reservation to ensure it exists
        Reservation reservation = new Reservation();
        reservation.setName("Test Reservation");
        ResponseEntity<Reservation> postResponse = restTemplate.postForEntity("/reservations", reservation, Reservation.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();

        int reservationId = postResponse.getBody().getId();

        // Delete the reservation
        restTemplate.delete("/reservations/" + reservationId);

        ResponseEntity<Reservation> response = restTemplate.getForEntity("/reservations/" + reservationId, Reservation.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

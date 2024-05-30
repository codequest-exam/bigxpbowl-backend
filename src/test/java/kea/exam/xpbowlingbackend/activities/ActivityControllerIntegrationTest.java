package kea.exam.xpbowlingbackend.activities;

import kea.exam.xpbowlingbackend.activity.entities.Activity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ActivityControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllActivitiesReturnsAllActivities() {
        ResponseEntity<Activity[]> response = restTemplate.getForEntity("/activities", Activity[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getActivityByIdReturnsActivity() {
        ResponseEntity<Activity> response = restTemplate.getForEntity("/activities/1", Activity.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getActivitiesByDateReturnsActivities() {
        ResponseEntity<Activity[]> response = restTemplate.getForEntity("/activities/date/{date}", Activity[].class, LocalDate.now());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getActivitiesOneWeekAheadReturnsActivities() {
        ResponseEntity<Activity[]> response = restTemplate.getForEntity("/activities/week/{date}", Activity[].class, LocalDate.now());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getActivitiesOneMonthAheadReturnsActivities() {
        ResponseEntity<Activity[]> response = restTemplate.getForEntity("/activities/month/{date}", Activity[].class, LocalDate.now());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}

package kea.exam.xpbowlingbackend.activities;

import kea.exam.xpbowlingbackend.activity.entities.Activity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActivityControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllActivitiesReturnsAllActivities() {
        ResponseEntity<Activity[]> response = restTemplate.getForEntity("/activity", Activity[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getActivityByIdReturnsActivity() {
        // Assuming there is an activity with id 1
        ResponseEntity<Activity> response = restTemplate.getForEntity("/activity/1", Activity.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getActivitiesByDateReturnsActivities() {
        // Assuming there are activities for the current date
        ResponseEntity<Activity[]> response = restTemplate.getForEntity("/activity/date/{date}", Activity[].class, LocalDate.now());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getActivitiesOneWeekAheadReturnsActivities() {
        // Assuming there are activities for the week ahead of the current date
        ResponseEntity<Activity[]> response = restTemplate.getForEntity("/activity/week/{date}", Activity[].class, LocalDate.now());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getActivitiesOneMonthAheadReturnsActivities() {
        // Assuming there are activities for the month ahead of the current date
        ResponseEntity<Activity[]> response = restTemplate.getForEntity("/activity/month/{date}", Activity[].class, LocalDate.now());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}

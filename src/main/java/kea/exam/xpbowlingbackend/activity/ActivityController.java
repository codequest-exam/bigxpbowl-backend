package kea.exam.xpbowlingbackend.activity;

import kea.exam.xpbowlingbackend.activity.entities.Activity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable int id) {
        return ResponseEntity.of(activityService.getActivityById(id)) ;
    }

    @GetMapping("/date/{date}")
    public List<Activity> getActivitiesByDate(@PathVariable LocalDate date) {
        return activityService.getActivitiesByDate(date);
    }

    @GetMapping("/week/{date}")
    public List<Activity> getActivitiesOneWeekAhead(@PathVariable LocalDate date) {
        return activityService.getActivitiesByWeek(date);
    }

    @GetMapping("/month/{date}")
    public List<Activity> getActivitiesOneMonthAhead(@PathVariable LocalDate date) {
        return activityService.getActivitiesByMonth(date);
    }
}

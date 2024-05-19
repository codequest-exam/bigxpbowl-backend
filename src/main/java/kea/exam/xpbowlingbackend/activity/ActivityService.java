package kea.exam.xpbowlingbackend.activity;

import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.activity.repositories.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    ActivityRepository activityRepository;


    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> saveAll(List<Activity> activities) {
        return activityRepository.saveAll(activities);
    }

    public boolean timeSlotAvailable(Activity activityToCheck) {
        final int airHokeyTables = 6;
        final int bowlingLanes = 24;
        final int diningTables = 20;

        List<Activity> activitiesOnTheDay = activityRepository.findAllByDate(activityToCheck.getDate());

        if (activityToCheck.getAirhockeyTables() != null) {
            test(activityToCheck, activitiesOnTheDay, airHokeyTables);
        }
        if (activityToCheck.getBowlingLanes() != null) {
            test(activityToCheck, activitiesOnTheDay, bowlingLanes);
        }
        if (activityToCheck.getDiningTables() != null) {
            test(activityToCheck, activitiesOnTheDay, diningTables);
        }


        return true;
    }

    public void test(Activity activityToCheck, List<Activity> activitiesOnTheDay, int totalAvailable) {
        int amountTaken = 0;
        for (Activity acti : activitiesOnTheDay) {
            if (activityOverlaps(activityToCheck, acti)) {
                amountTaken++;
                System.out.println("amount taken: " + amountTaken);
            }
            if (amountTaken >= totalAvailable - activityToCheck.getAirhockeyTables().size()) {
                throw new RuntimeException("No room at the time");
            }
        }

    }

    public boolean activityOverlaps(Activity activityToCheck, Activity activity) {
        return (activity.getEndTime().isAfter(activityToCheck.getStartTime()) && activity.getStartTime().compareTo(activityToCheck.getStartTime()) <= 0);
    }

}

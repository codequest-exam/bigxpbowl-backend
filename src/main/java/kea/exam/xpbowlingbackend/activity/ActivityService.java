package kea.exam.xpbowlingbackend.activity;

import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.activity.entities.AirhockeyTable;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import kea.exam.xpbowlingbackend.activity.entities.DiningTable;
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
        final int airHockeyTables = 6;
        final int bowlingLanes = 24;
        final int diningTables = 20;

        List<Activity> activitiesOnTheDay = activityRepository.findAllByDate(activityToCheck.getDate());


        List<Activity> bowlingActivities = activitiesOnTheDay.stream().filter(activity -> activity.getBowlingLanes() != null).toList();
        List<Activity> airHockeyActivities = activitiesOnTheDay.stream().filter(activity -> activity.getAirhockeyTables() != null).toList();
        List<Activity> diningActivities = activitiesOnTheDay.stream().filter(activity -> activity.getDiningTables() != null).toList();


        int bowlingTaken = 0;
        int diningTaken = 0;
        int airHockeyTaken = 0;

        for (Activity activity : activitiesOnTheDay) {

            if (activityToCheck.getBowlingLanes() instanceof List && activity.getBowlingLanes() instanceof List) {
                for (int i = 0; i < activity.getBowlingLanes().size(); i++) {
                    if (activityOverlaps(activityToCheck, activity)) {
                        bowlingTaken++;
                    }
                }

            }

            if (activityToCheck.getAirhockeyTables() instanceof List && activity.getAirhockeyTables() instanceof List) {
                for (int i = 0; i < activity.getAirhockeyTables().size(); i++) {
                    if (activityOverlaps(activityToCheck, activity)) {
                        airHockeyTaken++;
                    }
                }
            }

            if (activityToCheck.getDiningTables() instanceof List && activity.getDiningTables() != null) {
                for (int i = 0; i < activity.getDiningTables().size(); i++) {
                    if (activityOverlaps(activityToCheck, activity)) {
                        diningTaken++;
                    }
                }
            }

        }

        if (bowlingTaken + (activityToCheck.getBowlingLanes() == null ? 0 : activityToCheck.getBowlingLanes().size())  > bowlingLanes) {
            throw new IllegalArgumentException("No more bowling lanes available");
        }

        if (airHockeyTaken + (activityToCheck.getAirhockeyTables() == null ? 0 :activityToCheck.getAirhockeyTables().size()) > airHockeyTables) {
            throw new IllegalArgumentException("No more airhockey tables available");
        }

        if (diningTaken + (activityToCheck.getDiningTables() == null ? 0 :activityToCheck.getDiningTables().size()) > diningTables) {
            throw new IllegalArgumentException("No more dining tables available");
        }

//            if (activityOverlaps(activityToCheck, activity)) {
//                amountTaken++;
//                System.out.println("amount taken: " + amountTaken);
//            }
//            if (amountTaken+ activityToCheck.getAirhockeyTables().size() > totalAvailable) {
//                System.out.println("amount taken: " + amountTaken);
//                throw new IllegalArgumentException("No more tables available");
//            }

//        if (activityToCheck.getAirhockeyTables() instanceof List && activityToCheck.getAirhockeyTables().size() > 0) {
//            System.out.println("Airhockey activities: " + airHockeyActivities.size());
//            overLapCheck(activityToCheck, airHockeyActivities, AirhockeyTable, airHokeyTables);
//            System.out.println("Airhockey passed");
//        }
//        if (activityToCheck.getAirhockeyTables() instanceof List && activityToCheck.getBowlingLanes().size() > 0){
//            System.out.println("Bowling activities: " + bowlingActivities.size() );
//            overLapCheck(activityToCheck, bowlingActivities, bowlingLanes);
//            System.out.println("Bowling passed");
//        }
//        if (activityToCheck.getAirhockeyTables() instanceof List && activityToCheck.getDiningTables().size() > 0   ){
//            System.out.println("Dining activities: " + diningActivities.size());
//            overLapCheck(activityToCheck, diningActivities, diningTables);
//            System.out.println("Dining passed");
//        }


        return true;
    }

    public void overLapCheck(Activity activityToCheck, List<Activity> activitiesOnTheDay, int totalAvailable) {
        int amountTaken = 0;
        for (Activity acti : activitiesOnTheDay) {

            if (activityOverlaps(activityToCheck, acti)) {
                amountTaken++;
                System.out.println("amount taken: " + amountTaken);
            }
            if (amountTaken + activityToCheck.getAirhockeyTables().size() > totalAvailable) {
                System.out.println("amount taken: " + amountTaken);
                throw new IllegalArgumentException("No more tables available");
            }
        }

    }

    public boolean activityOverlaps(Activity activityToCheck, Activity activity) {
        return (activity.getEndTime().isAfter(activityToCheck.getStartTime()) && activity.getStartTime().compareTo(activityToCheck.getStartTime()) <= 0);
    }

}

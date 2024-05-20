package kea.exam.xpbowlingbackend.activity;

import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.activity.entities.AirhockeyTable;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import kea.exam.xpbowlingbackend.activity.entities.DiningTable;
import kea.exam.xpbowlingbackend.activity.repositories.ActivityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ActivityService {
    final int totalAirHockeyTables = 6;
    final int totalBowlingLanes = 24;
    final int totalDiningTables = 20;
    int bowlingTaken = 0;
    int diningTaken = 0;
    int airHockeyTaken = 0;

    ActivityRepository activityRepository;


    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> saveAll(List<Activity> activities) {
        return activityRepository.saveAll(activities);
    }

    public boolean setAvailableTableOrLane(Activity activityToCheck) {
        resetTakenCount();

        List<Activity> activitiesOnTheDay = activityRepository.findAllByDate(activityToCheck.getDate());


        System.out.println("amount of activities on the day:");
        System.out.println(activitiesOnTheDay.size());

        for (Activity activity : activitiesOnTheDay) {
            countIfOverlap(activityToCheck, activity);
        }
        setFirstAvailableTableOrLane(activityToCheck);

        throwIfExceedsLimit(activityToCheck);



        resetTakenCount();

        return true;
    }

    private void resetTakenCount() {
        bowlingTaken = 0;
        diningTaken = 0;
        airHockeyTaken = 0;
    }

    private void throwIfExceedsLimit(Activity activityToCheck) {
        System.out.println("bowling taken: " + bowlingTaken);
        System.out.println("dining taken: " + diningTaken);
        System.out.println("airhockey taken: " + airHockeyTaken);
        System.out.println("statement check " + (activityToCheck.getBowlingLanes() == null ? 0 : activityToCheck.getBowlingLanes().size()));

        if (newOrderExceedsLimit(bowlingTaken, activityToCheck.getBowlingLanes() == null ? 0 : activityToCheck.getBowlingLanes().size(), totalBowlingLanes)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No more bowling lanes available");
        }
        if (newOrderExceedsLimit(airHockeyTaken, activityToCheck.getAirhockeyTables() == null ? 0 : activityToCheck.getAirhockeyTables().size(), totalAirHockeyTables)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hockey tables available");
        }
        if (newOrderExceedsLimit(diningTaken, activityToCheck.getDiningTables() == null ? 0 : activityToCheck.getDiningTables().size(), totalDiningTables)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No more dining tables available");
        }
    }

    private void setFirstAvailableTableOrLane(Activity activityToCheck) {
        if (activityToCheck.getBowlingLanes() != null) {
            for (int i = 0; i < activityToCheck.getBowlingLanes().size(); i++) {
                activityToCheck.getBowlingLanes().get(i).setLaneNumber(bowlingTaken + i + 1);
            }
        }
        if (activityToCheck.getAirhockeyTables() != null) {
            for (int i = 0; i < activityToCheck.getAirhockeyTables().size(); i++) {
                activityToCheck.getAirhockeyTables().get(i).setTableNumber(airHockeyTaken + i + 1);
            }
        }
        if (activityToCheck.getDiningTables() != null) {
            for (int i = 0; i < activityToCheck.getDiningTables().size(); i++) {
                activityToCheck.getDiningTables().get(i).setTableNumber(diningTaken + i + 1);
            }
        }
    }

    private void countIfOverlap(Activity activityToCheck, Activity activity) {
        if (activityToCheck.getBowlingLanes() != null && activity.getBowlingLanes() != null) {
            for (int i = 0; i < activity.getBowlingLanes().size(); i++) {
                if (activityOverlaps(activityToCheck, activity)) {
                    bowlingTaken++;
                }
            }
        }

        if (activityToCheck.getAirhockeyTables() != null && activity.getAirhockeyTables() != null) {
            for (int i = 0; i < activity.getAirhockeyTables().size(); i++) {
                if (activityOverlaps(activityToCheck, activity)) {
                    airHockeyTaken++;
                }
            }
        }

        if (activityToCheck.getDiningTables() != null && activity.getDiningTables() != null) {
            for (int i = 0; i < activity.getDiningTables().size(); i++) {
                if (activityOverlaps(activityToCheck, activity)) {
                    diningTaken++;
                }
            }
        }
    }

    public boolean newOrderExceedsLimit(int taken, int newOrder, int limit) {
        return taken + newOrder > limit;
    }

    public boolean activityOverlaps(Activity activityToCheck, Activity activity) {
        return (activity.getEndTime().isAfter(activityToCheck.getStartTime()) && activity.getStartTime().compareTo(activityToCheck.getStartTime()) <= 0);
    }

    public boolean timeSlotAvailableOnSpecificTableOrLane(Activity incoming) {

        List<Activity> activitiesOnTheDay = activityRepository.findAllByDate(incoming.getDate());
        for (Activity existingActivity : activitiesOnTheDay) {
            if (existingActivity.getBowlingLanes() != null && incoming.getBowlingLanes() != null) {
                for (BowlingLane bowlingLane : incoming.getBowlingLanes()) {
                    if (bowlingLaneMatch(bowlingLane, existingActivity.getBowlingLanes()) && activityOverlaps(incoming, existingActivity)) {
                        return false;
                    }
                }
            }

            if (existingActivity.getAirhockeyTables() != null && incoming.getAirhockeyTables() != null) {
                for (AirhockeyTable airhockeyTable : incoming.getAirhockeyTables()) {
                    if (airhockeyTableMatch(airhockeyTable, existingActivity.getAirhockeyTables()) && activityOverlaps(incoming, existingActivity)) {
                        return false;
                    }
                }
            }

            if (existingActivity.getDiningTables() != null && incoming.getDiningTables() != null) {
                for (DiningTable diningTable : incoming.getDiningTables()) {
                    if (diningTableMatch(diningTable, existingActivity.getDiningTables()) && activityOverlaps(incoming, existingActivity)) {
                        return false;
                    }
                }
            }

        }

        return true;
    }

    private boolean bowlingLaneMatch(BowlingLane bowlingLane, List<BowlingLane> existingBowlingLanes) {
        for (BowlingLane existing : existingBowlingLanes) {
            if (existing.getLaneNumber() == bowlingLane.getLaneNumber()) {
                return true;
            }
        }
        return false;
    }

    private boolean airhockeyTableMatch(AirhockeyTable airhockeyTable, List<AirhockeyTable> existingAirhockeyTables) {
        for (AirhockeyTable existing : existingAirhockeyTables) {
            if (existing.getTableNumber() == airhockeyTable.getTableNumber()) {
                return true;
            }
        }
        return false;
    }

    private boolean diningTableMatch(DiningTable diningTable, List<DiningTable> existingDiningTables) {
        for (DiningTable existing : existingDiningTables) {
            if (existing.getTableNumber() == diningTable.getTableNumber()) {
                return true;
            }
        }
        return false;
    }


}

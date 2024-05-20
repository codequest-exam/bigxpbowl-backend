package kea.exam.xpbowlingbackend.activity;

import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.activity.entities.AirhockeyTable;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import kea.exam.xpbowlingbackend.activity.entities.DiningTable;
import kea.exam.xpbowlingbackend.activity.repositories.ActivityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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


    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }


    public List<Activity> saveAll(List<Activity> activities) {
        return activityRepository.saveAll(activities);
    }

    public Optional<Activity> getActivityById(int id) {
        return activityRepository.findById(id);
    }


    public List<Activity> getActivitiesByDate(LocalDate date) {
        return activityRepository.findAllByDate(date);
    }

    public List<Activity> getActivitiesByWeek(LocalDate date) {
        return activityRepository.findAllByDateBetween(date, date.plusWeeks(1));
    }

    public List<Activity> getActivitiesByMonth(LocalDate date) {
        return activityRepository.findAllByDateBetween(date, date.plusMonths(1));
    }

    public boolean setAvailableTableOrLane(Activity activityToCheck) {
        resetTakenCount();

        List<Activity> activitiesOnTheDay = activityRepository.findAllByDate(activityToCheck.getDate());


        System.out.println("amount of activities on the day:");
        System.out.println(activitiesOnTheDay.size());

        for (Activity activity : activitiesOnTheDay) {
            if (activityToCheck.getId() == activityToCheck.getId()) {
                continue;
            }
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

    private void countIfOverlap(Activity activityToCheck, Activity existingActivity) {
        if (activityToCheck.getBowlingLanes() != null && existingActivity.getBowlingLanes() != null) {
            for (int i = 0; i < existingActivity.getBowlingLanes().size(); i++) {
                if (activityOverlaps(activityToCheck, existingActivity)) {
                    bowlingTaken++;
                }
            }
        }

        if (activityToCheck.getAirhockeyTables() != null && existingActivity.getAirhockeyTables() != null) {
            for (int i = 0; i < existingActivity.getAirhockeyTables().size(); i++) {
                if (activityOverlaps(activityToCheck, existingActivity)) {
                    airHockeyTaken++;
                }
            }
        }

        if (activityToCheck.getDiningTables() != null && existingActivity.getDiningTables() != null) {
            for (int i = 0; i < existingActivity.getDiningTables().size(); i++) {
                if (activityOverlaps(activityToCheck, existingActivity)) {
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
            if (existingActivity.getId() == incoming.getId()) {
                System.out.println("same id");
                continue;
            }
            else {
                System.out.println("not same id");
                System.out.println("existing activity id: " + existingActivity.getId());
                System.out.println("incoming activity id: " + incoming.getId());
            }
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

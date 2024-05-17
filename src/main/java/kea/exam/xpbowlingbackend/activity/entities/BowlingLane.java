package kea.exam.xpbowlingbackend.activity.entities;

import jakarta.persistence.*;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter

public class BowlingLane extends Bookable {
    private boolean childFriendly;
    private int laneNumber;


    public BowlingLane(ActivityType activityType, boolean maintenance, boolean childFriendly, boolean clubLane, int laneNumber ) {
        //this.setActivityType(activityType);
        this.setMaintenance(maintenance);
        this.childFriendly = childFriendly;

        this.laneNumber = laneNumber;
    }

    public BowlingLane(boolean maintenance, boolean childFriendly, boolean clubLane, int laneNumber ) {
        this.setMaintenance(maintenance);
        this.childFriendly = childFriendly;
        this.laneNumber = laneNumber;
    }

}

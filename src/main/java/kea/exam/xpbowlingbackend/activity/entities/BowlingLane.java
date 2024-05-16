package kea.exam.xpbowlingbackend.activity.entities;

import jakarta.persistence.*;
import kea.exam.xpbowlingbackend.activity.entities.ActivityType;
import kea.exam.xpbowlingbackend.activity.entities.Bookable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter

public class BowlingLane extends Bookable {
    private boolean childFriendly;
    private boolean clubLane;

    private int laneNumber;

    public BowlingLane(ActivityType activityType, boolean maintenance, boolean childFriendly, boolean clubLane, int laneNumber ) {
        this.setActivityType(activityType);
        this.setMaintenance(maintenance);
        this.childFriendly = childFriendly;
        this.clubLane = clubLane;
        this.laneNumber = laneNumber;
    }

}

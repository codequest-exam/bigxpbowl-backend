package kea.exam.xpbowlingbackend.activity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter

public class BowlingLane extends Bookable {
    @GeneratedValue
    @Id
    private int id;
    private boolean childFriendly;
    private boolean clubLane;

    private int laneNumber;

    public BowlingLane(ActivityType activityType, boolean maintenance, boolean childFriendly, boolean clubLane ) {
        this.setActivityType(activityType);
        this.setMaintenance(maintenance);
        this.childFriendly = childFriendly;
        this.clubLane = clubLane;
    }
    public BowlingLane(ActivityType activityType, boolean maintenance, boolean childFriendly, boolean clubLane, int laneNumber ) {
        this.setActivityType(activityType);
        this.setMaintenance(maintenance);
        this.childFriendly = childFriendly;
        this.clubLane = clubLane;
        this.laneNumber = laneNumber;
    }

}

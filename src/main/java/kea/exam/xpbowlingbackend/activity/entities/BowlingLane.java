package kea.exam.xpbowlingbackend.activity.entities;

import jakarta.persistence.*;
import kea.exam.xpbowlingbackend.reservation.RecurringBowlingReservation;
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
    private boolean clubLane;

    private int laneNumber;

    @OneToMany(mappedBy = "bowlingLane")
    private List<RecurringBowlingReservation> recurringBowlingReservationList;

    public BowlingLane(ActivityType activityType, boolean maintenance, boolean childFriendly, boolean clubLane, int laneNumber ) {
        this.setActivityType(activityType);
        this.setMaintenance(maintenance);
        this.childFriendly = childFriendly;
        this.clubLane = clubLane;
        this.laneNumber = laneNumber;
    }

}

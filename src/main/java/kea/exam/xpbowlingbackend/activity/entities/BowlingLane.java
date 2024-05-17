package kea.exam.xpbowlingbackend.activity.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
    private ActivityType activityType = ActivityType.BOWLING;

    public BowlingLane(boolean maintenance, boolean childFriendly, int laneNumber ) {
        this.setMaintenance(maintenance);
        this.childFriendly = childFriendly;

        this.laneNumber = laneNumber;
    }


}

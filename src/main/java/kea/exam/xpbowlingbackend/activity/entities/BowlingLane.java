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

public class BowlingLane {
    @Id
    private int laneNumber;
    private boolean maintenance;

    private boolean childFriendly;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;


    public BowlingLane(boolean maintenance, boolean childFriendly, int laneNumber, ActivityType activityType) {
        this.maintenance =maintenance;
        this.childFriendly = childFriendly;
        this.laneNumber = laneNumber;
        this.activityType = activityType;
    }


}

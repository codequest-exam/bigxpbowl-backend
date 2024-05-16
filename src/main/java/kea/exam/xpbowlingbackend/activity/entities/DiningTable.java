package kea.exam.xpbowlingbackend.activity.entities;

import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class DiningTable extends Bookable {
    private int tableNumber;
    public DiningTable(ActivityType activityType, boolean maintenance, int tableNumber){
        super(activityType, maintenance);
        this.tableNumber = tableNumber;
    }
}

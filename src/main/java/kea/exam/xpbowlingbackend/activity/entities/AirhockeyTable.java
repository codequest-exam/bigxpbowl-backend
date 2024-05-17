package kea.exam.xpbowlingbackend.activity.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AirhockeyTable extends Bookable {
    private int tableNumber;
    public AirhockeyTable(ActivityType activityType, boolean maintenance, int tableNumber){

        super( maintenance);
        //super(activityType, maintenance);
        this.tableNumber = tableNumber;
    }
}

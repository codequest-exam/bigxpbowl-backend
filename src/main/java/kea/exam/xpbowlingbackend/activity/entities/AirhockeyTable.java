package kea.exam.xpbowlingbackend.activity.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AirhockeyTable {

    @Id
    private int tableNumber;
    private boolean maintenance;
    @Enumerated(EnumType.STRING)
    private ActivityType activityType = ActivityType.AIRHOCKEY;

    public AirhockeyTable(boolean maintenance, int tableNumber){
        this.maintenance = maintenance;
        this.tableNumber = tableNumber;
    }
}

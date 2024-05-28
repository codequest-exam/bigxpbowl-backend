package kea.exam.xpbowlingbackend.activity.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class DiningTable {
    @Id
    private int tableNumber;
    private boolean maintenance;
    @Enumerated(EnumType.STRING)
    private ActivityType activityType = ActivityType.DINING;

    public DiningTable( boolean maintenance, int tableNumber) {
        this.maintenance = maintenance;
        this.tableNumber = tableNumber;
    }
}

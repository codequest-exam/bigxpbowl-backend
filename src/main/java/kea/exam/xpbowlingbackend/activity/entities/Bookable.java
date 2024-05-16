package kea.exam.xpbowlingbackend.activity.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
 @Inheritance(strategy =  InheritanceType.TABLE_PER_CLASS)
public abstract class Bookable {
    @GeneratedValue
    @Id
    private int id;
    @ManyToOne
    private ActivityType activityType;
    private boolean maintenance;

    public Bookable(ActivityType activityType, boolean maintenance) {
        this.setActivityType(activityType);
        this.setMaintenance(maintenance);
    }
}

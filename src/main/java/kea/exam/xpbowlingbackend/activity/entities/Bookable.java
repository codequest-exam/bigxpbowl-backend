package kea.exam.xpbowlingbackend.activity.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy =  InheritanceType.TABLE_PER_CLASS)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "activityType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BowlingLane.class, name = "BOWLING"),
        @JsonSubTypes.Type(value = DiningTable.class, name = "DINING"),
        @JsonSubTypes.Type(value = AirhockeyTable.class, name = "AIRHOCKEY")
})
public abstract class Bookable {
    @GeneratedValue
    @Id
    private int id;
    private boolean maintenance;

    //    @ManyToOne
//    private ActivityType activityType;
    public Bookable( boolean maintenance) {
        //this.setActivityType(activityType);
        this.setMaintenance(maintenance);
    }


//    public Bookable(ActivityType activityType, boolean maintenance) {
//        //this.setActivityType(activityType);
//        this.setMaintenance(maintenance);
//    }
}

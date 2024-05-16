package kea.exam.xpbowlingbackend.activity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class AirhockeyTable extends Bookable {
//    @GeneratedValue
//    @Id
//    private int id;

    public AirhockeyTable(ActivityType activityType, boolean maintenance){
        super(activityType, maintenance);
    }

//    @Override
//    public String toString() {
//        return "AirhockeyTable{" +
//                "id=" + id +
//                '}';
//    }
}

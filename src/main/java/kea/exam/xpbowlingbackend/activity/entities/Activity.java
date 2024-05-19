package kea.exam.xpbowlingbackend.activity.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Activity {
    @Id
    @GeneratedValue
    private int id;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;

//    @Enumerated(EnumType.STRING)
//    private ActivityType activityType;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<BowlingLane> bowlingLanes;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AirhockeyTable> airhockeyTables;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<DiningTable> diningTables;


    public Activity(LocalTime startTime, LocalTime endTime, LocalDate date, List<BowlingLane> bowlingLanes, List<DiningTable> diningTables, List<AirhockeyTable> airhockeyTables) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;

        this.bowlingLanes = bowlingLanes;
        this.airhockeyTables = airhockeyTables;
        this.diningTables = diningTables;
    }

//    public Activity(LocalTime startTime, LocalTime endTime, LocalDate date, List<BowlingLane> bowlingLanes) {
//        this(startTime, endTime, date, bowlingLanes, null, null);
//    }
//
//    public Activity(LocalTime startTime, LocalTime endTime, LocalDate date, List<DiningTable> diningTables) {
//        this(startTime, endTime, date, null, diningTables, null);
//    }
//    public Activity(LocalTime startTime, LocalTime endTime, LocalDate date,  List<AirhockeyTable> airhockeyTables) {
//        this(startTime, endTime, date, null, null, airhockeyTables);
//    }
    public Activity(LocalTime startTime, LocalTime endTime, LocalDate date) {
        this(startTime, endTime, date, null, null, null);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", date=" + date +
                ", bowlingLanes=" + bowlingLanes +
                ", airhockeyTables=" + airhockeyTables +
                ", diningTables=" + diningTables +
                '}';
    }
}

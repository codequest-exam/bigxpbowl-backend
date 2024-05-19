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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Activity {
    @Id
    @GeneratedValue
    private int id;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<BowlingLane> bowlingLanes = null;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AirhockeyTable> airhockeyTables = null;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<DiningTable> diningTables = null;


    public Activity(LocalTime startTime, LocalTime endTime, LocalDate date, List<BowlingLane> bowlingLanes, List<DiningTable> diningTables, List<AirhockeyTable> airhockeyTables) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;

        this.bowlingLanes = bowlingLanes;
        this.airhockeyTables = airhockeyTables;
        this.diningTables = diningTables;
    }

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

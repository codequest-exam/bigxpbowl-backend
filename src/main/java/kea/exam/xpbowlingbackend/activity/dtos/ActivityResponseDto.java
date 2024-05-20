package kea.exam.xpbowlingbackend.activity.dtos;

import kea.exam.xpbowlingbackend.activity.entities.AirhockeyTable;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import kea.exam.xpbowlingbackend.activity.entities.DiningTable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class ActivityResponseDto{
    private int id;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private List<BowlingLane> bowlingLanes;
    private List<DiningTable> diningTables;
    private List<AirhockeyTable> airhockeyTables;

    public ActivityResponseDto() {
    }

    public ActivityResponseDto(int id, LocalTime startTime, LocalTime endTime, LocalDate date, List<BowlingLane> bowlingLanes, List<DiningTable> diningTables, List<AirhockeyTable> airhockeyTables) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.bowlingLanes = bowlingLanes;
        this.diningTables = diningTables;
        this.airhockeyTables = airhockeyTables;
    }

}

//public class ActivityResponseDto(int id, LocalTime startTime, LocalTime endTime, String date, List<BowlingLane> bowlingLanes, List<DiningTable> diningTables, List<AirhockeyTable> airhockeyTables) {
//
//    // only return non-null values
//    public ActivityResponseDto {
//        bowlingLanes = bowlingLanes != null ? bowlingLanes : List.of();
//        diningTables = diningTables != null ? diningTables : List.of();
//        airhockeyTables = airhockeyTables != null ? airhockeyTables : List.of();
//    }
//
//}


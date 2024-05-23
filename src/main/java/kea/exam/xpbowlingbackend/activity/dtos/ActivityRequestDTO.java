package kea.exam.xpbowlingbackend.activity.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public record ActivityRequestDTO(LocalTime startTime, LocalTime endTime, LocalDate date, int childLanes, int bowlingLanes, int diningTables,int airhockeyTables) {
}

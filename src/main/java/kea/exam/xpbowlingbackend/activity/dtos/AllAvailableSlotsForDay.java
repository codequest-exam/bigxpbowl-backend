package kea.exam.xpbowlingbackend.activity.dtos;

import java.util.List;

public record AllAvailableSlotsForDay(List<AvailableResponseDTO> bowlingLanes, List<AvailableResponseDTO> childLanes,List<AvailableResponseDTO> airHockeyTables,List<AvailableResponseDTO> diningTables ) {
}

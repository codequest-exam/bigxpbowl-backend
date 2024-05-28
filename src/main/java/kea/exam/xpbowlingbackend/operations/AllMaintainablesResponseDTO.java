package kea.exam.xpbowlingbackend.operations;

import kea.exam.xpbowlingbackend.activity.entities.AirhockeyTable;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import kea.exam.xpbowlingbackend.activity.entities.DiningTable;

import java.util.List;

public record AllMaintainablesResponseDTO(List<BowlingLane> bowlingLanes, List<DiningTable> diningTables, List<AirhockeyTable> airhockeyTables) {
}

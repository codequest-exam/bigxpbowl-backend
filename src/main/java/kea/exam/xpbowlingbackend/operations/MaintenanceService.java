package kea.exam.xpbowlingbackend.operations;

import kea.exam.xpbowlingbackend.activity.entities.ActivityType;
import kea.exam.xpbowlingbackend.activity.entities.AirhockeyTable;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import kea.exam.xpbowlingbackend.activity.entities.DiningTable;
import kea.exam.xpbowlingbackend.activity.repositories.AirhockeyTableRepository;
import kea.exam.xpbowlingbackend.activity.repositories.BowlingLaneRepository;
import kea.exam.xpbowlingbackend.activity.repositories.DiningTableRepository;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceService {
    private final AirhockeyTableRepository airhockeyTableRepository;
    private final BowlingLaneRepository bowlingLaneRepository;
    private final DiningTableRepository diningTableRepository;

    public MaintenanceService(AirhockeyTableRepository airhockeyTableRepository, BowlingLaneRepository bowlingLaneRepository, DiningTableRepository diningTableRepository) {
        this.airhockeyTableRepository = airhockeyTableRepository;
        this.bowlingLaneRepository = bowlingLaneRepository;
        this.diningTableRepository = diningTableRepository;
    }


    public AllMaintainablesResponseDTO getAllMaintainables() {
        return new AllMaintainablesResponseDTO(
                bowlingLaneRepository.findAll(),
                diningTableRepository.findAll(),
                airhockeyTableRepository.findAll()
        );
    }


    public DiningTable changeMaintenanceStateDining(int id) {
        DiningTable diningTable = diningTableRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No dining table with id " + id + " found"));
        diningTable.setMaintenance(!diningTable.isMaintenance());
        return diningTableRepository.save(diningTable);
    }

    public AirhockeyTable changeMaintenanceStateAirHockey(int id) {
        AirhockeyTable airhockeyTable = airhockeyTableRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No airhockey table with id " + id + " found"));
        airhockeyTable.setMaintenance(!airhockeyTable.isMaintenance());
        return airhockeyTableRepository.save(airhockeyTable);
    }

    public BowlingLane changeMaintenanceStateBowling(int id) {
        BowlingLane bowlingLane = bowlingLaneRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No bowling lane with id " + id + " found"));
        bowlingLane.setMaintenance(!bowlingLane.isMaintenance());
        return bowlingLaneRepository.save(bowlingLane);
    }
}

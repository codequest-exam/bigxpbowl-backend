package kea.exam.xpbowlingbackend.operations;

import kea.exam.xpbowlingbackend.activity.entities.AirhockeyTable;
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
}

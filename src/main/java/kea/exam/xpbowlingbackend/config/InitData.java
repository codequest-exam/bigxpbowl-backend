package kea.exam.xpbowlingbackend.config;

import kea.exam.xpbowlingbackend.activity.ActivityType;
import kea.exam.xpbowlingbackend.activity.AirhockeyTable;
import kea.exam.xpbowlingbackend.activity.BowlingLane;
import kea.exam.xpbowlingbackend.activity.repositories.ActivityTypeRepository;
import kea.exam.xpbowlingbackend.activity.repositories.AirhockeyTableRepository;
import kea.exam.xpbowlingbackend.activity.repositories.BowlingLaneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {

    private final BowlingLaneRepository bowlingLaneRepository;
    private final AirhockeyTableRepository airhockeyTableRepository;
    private final ActivityTypeRepository activityTypeRepository;

    public InitData(BowlingLaneRepository bowlingLaneRepository, ActivityTypeRepository activityTypeRepository, AirhockeyTableRepository airhockeyTableRepository) {
        this.bowlingLaneRepository = bowlingLaneRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.airhockeyTableRepository = airhockeyTableRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        var bowling = activityTypeRepository.save(new ActivityType("bowling"));
        var airhockey = activityTypeRepository.save(new ActivityType("airhockey"));
        BowlingLane lane = bowlingLaneRepository.save(new BowlingLane( bowling,false, false, false, 1));
        AirhockeyTable airhockeyTable = airhockeyTableRepository.save(new AirhockeyTable(airhockey, false));

        var tables = airhockeyTableRepository.findAll();
        for (AirhockeyTable table : tables) {
            System.out.println(table.getId());
        }
    }
}

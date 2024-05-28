package kea.exam.xpbowlingbackend.operations;

import kea.exam.xpbowlingbackend.activity.entities.ActivityType;
import kea.exam.xpbowlingbackend.activity.entities.AirhockeyTable;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import kea.exam.xpbowlingbackend.activity.entities.DiningTable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping("/all")
    public AllMaintainablesResponseDTO getAllMaintainables() {
        return maintenanceService.getAllMaintainables();
    }


    @GetMapping("/change/BOWLING/{id}")
    public BowlingLane changeMaintenanceStateBowling(@PathVariable int id) {
        System.out.println("Changing maintenance with id " + id);
        return maintenanceService.changeMaintenanceStateBowling(id);
    }
    @GetMapping("/change/AIRHOCKEY/{id}")
    public AirhockeyTable changeMaintenanceStateAirHockey(@PathVariable int id) {
        System.out.println("Changing maintenance state with id " + id);
        return maintenanceService.changeMaintenanceStateAirHockey(id);
    }
    @GetMapping("/change/DINING/{id}")
    public DiningTable changeMaintenanceStateDining(@PathVariable int id) {
        System.out.println("Changing maintenance state with id " + id);
        return maintenanceService.changeMaintenanceStateDining(id);
    }
}

package kea.exam.xpbowlingbackend.operations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // TODO: Find the best way to change maintenance state of a maintainable
    //@PostMapping("/")
}

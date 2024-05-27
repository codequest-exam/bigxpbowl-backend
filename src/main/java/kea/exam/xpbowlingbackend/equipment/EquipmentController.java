package kea.exam.xpbowlingbackend.equipment;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class EquipmentController {
    EquipmentService equipmentService;
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }
    @GetMapping
    public List<Equipment> getAllEquipment() {
        return equipmentService.getAllEquipment();
    }
}

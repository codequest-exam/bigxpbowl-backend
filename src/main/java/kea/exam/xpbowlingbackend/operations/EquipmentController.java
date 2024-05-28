package kea.exam.xpbowlingbackend.operations;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    EquipmentService equipmentService;
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }
    @GetMapping
    public List<Equipment> getAllEquipment() {
        return equipmentService.getAllEquipment();
    }
    @PutMapping("/{id}")
    public Equipment updateEquipment(@PathVariable int id, @RequestBody Equipment equipment) {
        return equipmentService.updateEquipment(id, equipment);
    }


}

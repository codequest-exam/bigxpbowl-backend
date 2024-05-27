package kea.exam.xpbowlingbackend.equipment;

import java.util.List;

public class EquipmentService {
    EquipmentRepository equipmentRepository;
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }
}

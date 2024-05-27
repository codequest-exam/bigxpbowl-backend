package kea.exam.xpbowlingbackend.equipment;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EquipmentService {
    EquipmentRepository equipmentRepository;
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }
}

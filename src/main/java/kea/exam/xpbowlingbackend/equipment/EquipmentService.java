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
public Equipment updateEquipment(int id, Equipment equipment) {
        Equipment equipmentToUpdate = equipmentRepository.findById(id).orElse(null);
        if (equipmentToUpdate == null) {
            return null;
        }
        equipmentToUpdate.setName(equipment.getName());
        equipmentToUpdate.setStock(equipment.getStock());
        return equipmentRepository.save(equipmentToUpdate);
    }
}

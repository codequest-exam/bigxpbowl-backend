package kea.exam.xpbowlingbackend.staff;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ShiftService {
    private final ShiftRepository shiftRepository;
    private final StaffRepository staffRepository;

    public ShiftService(ShiftRepository shiftRepository, StaffRepository staffRepository) {
        this.shiftRepository = shiftRepository;
        this.staffRepository = staffRepository;
    }

    public List<Shift> getShifts() {
        return shiftRepository.findAll();
    }

    public Shift getShift(int id) {
        return shiftRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shift not found"));
    }

    public Shift changeStaff(int id, int staffId, int previousStaffId) {
        Shift shift = getShift(id);

        if (staffRepository.existsById(staffId)) {
            if (!shift.getStaff().isEmpty() && isStaffAssigned(shift, staffId)) {
                shift.getStaff().removeIf(staff -> staff.getId() == staffId);
            }
            else {
                Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff not found"));
                shift.getStaff().add(staff);
            }
            return shiftRepository.save(shift);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff not found");
        }
    }

    private boolean isStaffAssigned(Shift shift, int staffId) {
        return shift.getStaff().stream().anyMatch(staff -> staff.getId() == staffId);
    }

    public List<Staff> getStaff() {
        return staffRepository.findAll();
    }

    public Shift updateShift(int id, Shift shift) {
        if (shiftRepository.existsById(id)) {
            shift.setId(id);
            return shiftRepository.save(shift);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shift not found");
        }
    }
}

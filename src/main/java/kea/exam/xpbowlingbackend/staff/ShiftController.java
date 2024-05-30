package kea.exam.xpbowlingbackend.staff;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shifts")
public class ShiftController {
    private final ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @GetMapping("/all")
    public List<Shift> getShifts() {
        return shiftService.getShifts();
    }

    @GetMapping("/{id}")
    public Shift getShift(@PathVariable int id) {
        return shiftService.getShift(id);
    }

    @GetMapping("/staff")
    public List<Staff> getStaff() {
        return shiftService.getStaff();
    }

    @GetMapping("/{id}/staff/{newStaffId}/{previousStaffId}")
    public Shift changeStaff(@PathVariable int id, @PathVariable int newStaffId, @PathVariable int previousStaffId) {
        return shiftService.changeStaff(id, newStaffId, previousStaffId);
    }

    @PutMapping("/{id}")
    public Shift updateShift(@PathVariable int id, @RequestBody Shift shift) {
        return shiftService.updateShift(id, shift);
    }
}

package kea.exam.xpbowlingbackend.staff;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shift")
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

    @PostMapping("/{id}/staff/{staffId}")
    public Shift changeStaff(@PathVariable int id, @PathVariable int staffId) {
        return shiftService.changeStaff(id, staffId);
    }
}

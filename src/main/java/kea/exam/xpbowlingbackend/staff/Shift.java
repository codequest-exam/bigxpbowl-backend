package kea.exam.xpbowlingbackend.staff;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Shift {
    @Id
    @GeneratedValue
    private int id;
    private LocalTime shiftStart;
    private LocalTime shiftEnd;
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    @ManyToMany
    private List<Staff> staff;

    public Shift(LocalTime shiftStart, LocalTime shiftEnd, DayOfWeek dayOfWeek, List<Staff> staff) {
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.dayOfWeek = dayOfWeek;
        this.staff = staff;
    }
}

package kea.exam.xpbowlingbackend.staff;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Staff {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private StaffRoles role;

    public Staff(String name, StaffRoles role) {
        this.name = name;
        this.role = role;
    }
}

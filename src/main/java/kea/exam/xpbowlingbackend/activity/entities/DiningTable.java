package kea.exam.xpbowlingbackend.activity.entities;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class DiningTable {
    @Id
    private int tableNumber;
    private boolean maintenance;


    public DiningTable( boolean maintenance, int tableNumber) {
        this.maintenance = maintenance;
        this.tableNumber = tableNumber;
    }
}

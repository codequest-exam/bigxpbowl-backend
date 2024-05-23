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
public class AirhockeyTable {

    @Id
    private int tableNumber;
    private boolean maintenance;

    public AirhockeyTable(boolean maintenance, int tableNumber){
        this.maintenance = maintenance;
        this.tableNumber = tableNumber;
    }
}

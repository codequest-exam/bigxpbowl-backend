package kea.exam.xpbowlingbackend.equipment;

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
public class Equipment {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int stock;
    public Equipment(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }
}

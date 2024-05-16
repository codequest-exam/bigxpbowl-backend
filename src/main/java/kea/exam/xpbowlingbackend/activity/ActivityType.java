package kea.exam.xpbowlingbackend.activity;

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

public class ActivityType {
    @Id
//    @GeneratedValue
//    private int id;
    private String name;

    public ActivityType(String name) {
        this.name = name;
    }
}

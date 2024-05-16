package kea.exam.xpbowlingbackend.activity.entities;

import jakarta.persistence.Entity;
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
    private String name;
    public ActivityType(String name) {
        this.name = name;
    }
}

package kea.exam.xpbowlingbackend.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import kea.exam.xpbowlingbackend.activity.entities.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Reservation extends BookingDetails {

    @OneToMany
    private List<Activity> activities;

    public Reservation(String phoneNumber, String name, int particpants, List<Activity> activities) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.particpants = particpants;
        this.activities = activities;
    }
}

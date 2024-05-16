package kea.exam.xpbowlingbackend.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import kea.exam.xpbowlingbackend.activity.entities.Activity;

import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private int id;
    private String phoneNumber;
    private String name;
    private int particpants;

    @OneToMany
    private List<Activity> activities;

    public Reservation(String phoneNumber, String name, int particpants, List<Activity> activities) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.particpants = particpants;
        this.activities = activities;
    }
}

package kea.exam.xpbowlingbackend.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private int id;
    private String phoneNumber;
    private String name;
    private int particpants;
}

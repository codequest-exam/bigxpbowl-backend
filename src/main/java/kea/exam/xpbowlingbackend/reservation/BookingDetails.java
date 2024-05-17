package kea.exam.xpbowlingbackend.reservation;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy =  InheritanceType.TABLE_PER_CLASS)
abstract public class BookingDetails {
    @Id
    @GeneratedValue
    private int id;

    protected String phoneNumber;
    protected String name;
    protected int participants;
}

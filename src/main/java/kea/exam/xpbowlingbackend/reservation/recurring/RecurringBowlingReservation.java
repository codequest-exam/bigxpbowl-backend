package kea.exam.xpbowlingbackend.reservation.recurring;

// RecurringReservation.java
import jakarta.persistence.*;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import kea.exam.xpbowlingbackend.reservation.BookingDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class RecurringBowlingReservation extends BookingDetails {
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public RecurringBowlingReservation(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek, String number, String name, int i) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.phoneNumber = number;
        this.name = name;
        this.participants = i;
    }
}
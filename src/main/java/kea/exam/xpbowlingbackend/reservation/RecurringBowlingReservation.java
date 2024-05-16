package kea.exam.xpbowlingbackend.reservation;

// RecurringReservation.java
import jakarta.persistence.*;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
public class RecurringBowlingReservation {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private BowlingLane bowlingLane;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;

    // getters and setters

}
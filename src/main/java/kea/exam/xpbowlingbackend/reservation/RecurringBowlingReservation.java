package kea.exam.xpbowlingbackend.reservation;

// RecurringReservation.java
import jakarta.persistence.*;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
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

}
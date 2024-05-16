package kea.exam.xpbowlingbackend.activity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue
    private int id;
    private String startTime;
    private String endTime;
    private LocalDate date;

    @ManyToOne
    private ActivityType activityType;

    @ManyToMany
    private List<Bookable> bookableIds;
}

package kea.exam.xpbowlingbackend.activity.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
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
    private List<Bookable> bookables;

    public Activity(ActivityType activityType, String startTime, String endTime, LocalDate date , List<Bookable> bookables) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.activityType = activityType;
        this.bookables = bookables;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", date=" + date +
                ", activityType=" + activityType +
                ", bookables=" + bookables +
                '}';
    }
}

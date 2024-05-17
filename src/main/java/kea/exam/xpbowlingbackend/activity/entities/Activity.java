package kea.exam.xpbowlingbackend.activity.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Activity {
    @Id
    @GeneratedValue
    private int id;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;

    private String activityType;
    @ManyToMany
    private List<Bookable> bookables;

    public Activity(ActivityType activityType, LocalTime startTime, LocalTime endTime, LocalDate date , List<Bookable> bookables) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.activityType = activityType.getName();
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

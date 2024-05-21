package kea.exam.xpbowlingbackend.activity.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Activity {
    @Id
    @GeneratedValue
    private int id;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    private int amountBooked;




    public Activity(LocalTime startTime, LocalTime endTime, LocalDate date, ActivityType activityType, int amountBooked) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.activityType = activityType;
        this.amountBooked = amountBooked;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Activity) {
            Activity other = (Activity) obj;
            return this.id == other.id;
        }
        return false;
    }
}

package kea.exam.xpbowlingbackend.activity.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

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
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            property = "activityType")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = BowlingLane.class, name = "BOWLING"),
            @JsonSubTypes.Type(value = DiningTable.class, name = "DINING"),
            @JsonSubTypes.Type(value = AirhockeyTable.class, name = "AIRHOCKEY")
    })
    private List<Bookable> bookables;

    public Activity(ActivityType activityType, LocalTime startTime, LocalTime endTime, LocalDate date , List<Bookable> bookables) {
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

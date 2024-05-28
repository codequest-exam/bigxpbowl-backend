package kea.exam.xpbowlingbackend.activity.repositories;

import kea.exam.xpbowlingbackend.activity.entities.Activity;
import kea.exam.xpbowlingbackend.activity.entities.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    List<Activity> findAllByDate(LocalDate date);

    List<Activity> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Activity> findAllByDateAndStartTimeBetween(LocalDate date, LocalTime startTime, LocalTime endTime);

    List<Activity> findAllByDateAndActivityType(LocalDate date, ActivityType activityType);
}

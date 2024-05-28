package kea.exam.xpbowlingbackend.activity.dtos;

import kea.exam.xpbowlingbackend.activity.entities.ActivityType;

import java.time.LocalDate;
import java.time.LocalTime;

public record AvailableRequestDTO(LocalDate date, LocalTime startTime, LocalTime endTime, ActivityType activityType) {
}

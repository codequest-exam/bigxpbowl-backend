package kea.exam.xpbowlingbackend.activity.dtos;

import kea.exam.xpbowlingbackend.activity.entities.ActivityType;

public record ActivityResponseDTO(ActivityType activityType, String activityTime) {
}



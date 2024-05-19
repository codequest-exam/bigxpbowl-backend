package kea.exam.xpbowlingbackend.reservation;

import kea.exam.xpbowlingbackend.activity.dtos.ActivityResponseDto;

import java.util.List;

public record ReservationResponseDto(int id, String phoneNumber, String name, int participants, List<ActivityResponseDto> activities) {
}

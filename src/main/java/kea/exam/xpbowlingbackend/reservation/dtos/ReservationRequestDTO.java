package kea.exam.xpbowlingbackend.reservation.dtos;

import kea.exam.xpbowlingbackend.activity.dtos.ActivityRequestDTO;

import java.util.List;

public record ReservationRequestDTO(String name, String phoneNumber, int participants, List<ActivityRequestDTO> activities) {
}

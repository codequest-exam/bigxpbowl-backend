package kea.exam.xpbowlingbackend.activity.dtos;

import java.time.LocalTime;

public record AvailableResponseDTO(LocalTime hour, int amountAvailable) {
}

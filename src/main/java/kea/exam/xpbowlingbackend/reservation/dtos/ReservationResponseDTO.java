package kea.exam.xpbowlingbackend.reservation.dtos;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservationResponseDTO(int id, String name, String phoneNumber, int participants, LocalDate date, LocalTime startTime, LocalTime endTime, List<String> activities) {
}

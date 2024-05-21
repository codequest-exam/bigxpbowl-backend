package kea.exam.xpbowlingbackend.reservation.dtos;

import kea.exam.xpbowlingbackend.reservation.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DTOConverter {

    public static ReservationResponseDTO convertToDTO(Reservation reservation) {
        // Assuming all activities in a reservation have the same date
        LocalDate date = reservation.getActivities().isEmpty() ? null : reservation.getActivities().get(0).getDate();
        List<String> activities = reservation.getActivities().stream()
                .map(activity -> activity.getActivityType().name())
                .collect(Collectors.toList());

        return new ReservationResponseDTO(
                reservation.getId(),
                reservation.getName(),
                reservation.getPhoneNumber(),
                reservation.getParticipants(),
                date,
                activities
        );
    }
}

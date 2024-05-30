package kea.exam.xpbowlingbackend.activity.dtos;

import java.util.List;

public record AllAvailableSlotsForDay(List<AvailableResponseDTO> BOWLING, List<AvailableResponseDTO> CHILDBOWLING,List<AvailableResponseDTO> AIRHOCKEY,List<AvailableResponseDTO> DINING ) {
}

package kea.exam.xpbowlingbackend.reservation.recurring;

import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecurringBowlingReservationRepository extends JpaRepository<RecurringBowlingReservation, Integer>{
}

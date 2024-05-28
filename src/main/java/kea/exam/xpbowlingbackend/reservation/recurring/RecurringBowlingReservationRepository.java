package kea.exam.xpbowlingbackend.reservation.recurring;

import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface RecurringBowlingReservationRepository extends JpaRepository<RecurringBowlingReservation, Integer>{
    List<RecurringBowlingReservation> findAllByDayOfWeek(DayOfWeek dayOfWeek);
}

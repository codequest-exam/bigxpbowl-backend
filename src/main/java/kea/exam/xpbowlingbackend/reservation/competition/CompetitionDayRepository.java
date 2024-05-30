package kea.exam.xpbowlingbackend.reservation.competition;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface CompetitionDayRepository extends JpaRepository<CompetitionDay, Integer> {
    boolean existsByDate(LocalDate date);
}

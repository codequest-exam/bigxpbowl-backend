package kea.exam.xpbowlingbackend.activity.repositories;

import kea.exam.xpbowlingbackend.activity.entities.CompetitionDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionDayRepository extends JpaRepository<CompetitionDay, Integer>{
}

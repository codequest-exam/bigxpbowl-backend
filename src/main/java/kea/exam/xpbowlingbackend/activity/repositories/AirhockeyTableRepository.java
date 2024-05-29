package kea.exam.xpbowlingbackend.activity.repositories;

import kea.exam.xpbowlingbackend.activity.entities.AirhockeyTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirhockeyTableRepository extends JpaRepository<AirhockeyTable, Integer> {
    int countByMaintenance(boolean maintenance);
}

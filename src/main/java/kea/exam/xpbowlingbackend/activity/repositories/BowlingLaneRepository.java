package kea.exam.xpbowlingbackend.activity.repositories;

import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BowlingLaneRepository extends JpaRepository<BowlingLane, Integer> {
}

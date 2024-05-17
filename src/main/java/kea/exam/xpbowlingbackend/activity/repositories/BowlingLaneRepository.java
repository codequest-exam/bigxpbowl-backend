package kea.exam.xpbowlingbackend.activity.repositories;

import kea.exam.xpbowlingbackend.activity.entities.Bookable;
import kea.exam.xpbowlingbackend.activity.entities.BowlingLane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BowlingLaneRepository extends JpaRepository<BowlingLane, Integer> {
    List<Bookable> findByLaneNumber(int i);
}

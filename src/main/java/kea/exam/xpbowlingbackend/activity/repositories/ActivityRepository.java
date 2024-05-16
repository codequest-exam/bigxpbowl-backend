package kea.exam.xpbowlingbackend.activity.repositories;

import kea.exam.xpbowlingbackend.activity.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
}

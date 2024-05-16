package kea.exam.xpbowlingbackend.activity.repositories;

import kea.exam.xpbowlingbackend.activity.entities.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityTypeRepository extends JpaRepository<ActivityType, String> {
}

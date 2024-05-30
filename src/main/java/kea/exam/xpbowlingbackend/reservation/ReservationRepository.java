package kea.exam.xpbowlingbackend.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Page<Reservation> findAllByNameNotNull(Pageable page);
}

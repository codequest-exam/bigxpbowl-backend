package kea.exam.xpbowlingbackend.reservation;

import kea.exam.xpbowlingbackend.reservation.competition.CompetitionDay;
import kea.exam.xpbowlingbackend.reservation.dtos.ReservationResponseDTO;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponseDTO> getAllReservations(){
        return reservationService.getAllReservations();
    }
    @GetMapping("/recurring")
    public List<RecurringBowlingReservation> getAllRecurringReservations(){
        return reservationService.getAllRecurringReservations();
    }
    @GetMapping("/competition-day")
    public List<CompetitionDay> getAllCompetitionDays(){
        return reservationService.getAllCompetitionDays();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable int id){
        System.out.println("id: " + id);
        Optional<Reservation> opt = reservationService.getReservationById(id);
        if (opt.isPresent()) {
            System.out.println(opt.get().getName());
            return ResponseEntity.ok(opt.get());
        }
        else {
            System.out.println("NOT FOUND");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Reservation> createStandardReservation(@RequestBody Reservation reservation){
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(reservation)) ;
    }

    @PostMapping("/recurring")
    public RecurringBowlingReservation createRecurringReservation(@RequestBody RecurringBowlingReservation recurringBowlingReservation){
        return reservationService.createRecurringReservation(recurringBowlingReservation);
    }


    @PutMapping("/{id}")
    public Reservation updateStandardReservation(@PathVariable int id, @RequestBody Reservation reservation){
        return reservationService.updateReservationGeneral(id, reservation) ;
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable int id){
        reservationService.deleteReservation(id);
    }

}

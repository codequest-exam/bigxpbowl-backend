package kea.exam.xpbowlingbackend.reservation;

import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getAllReservations(){
        return reservationService.getAllReservations();
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
            //throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation with id " + id + " not found");
        }
    }

    @PostMapping
    public Reservation createStandardReservation(@RequestBody Reservation reservation){
        return reservationService.createReservation(reservation);
    }

    @PostMapping("/recurring")
    public RecurringBowlingReservation createRecurringReservation(@RequestBody RecurringBowlingReservation recurringBowlingReservation){
        return reservationService.createRecurringReservation(recurringBowlingReservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable int id, @RequestBody Reservation reservation){
        return ResponseEntity.of(reservationService.updateReservation(id, reservation)) ;
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable int id){
        reservationService.deleteReservation(id);
    }


//    @ExceptionHandler({Throwable.class})
//    public String handleException(){
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation with id " + id + " not found");
//    }

}

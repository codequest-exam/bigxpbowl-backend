//package kea.exam.xpbowlingbackend.exceptions;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.server.ResponseStatusException;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
//        System.out.println(ex.getStatusCode());
//        System.out.println(ex.getReason());
//        throw new ResponseStatusException (ex.getStatusCode(),ex.getReason());
//    }
//
//    @ExceptionHandler(TimeSlotNotAvailableException.class)
//    public ResponseEntity<String> handleTimeSlotNotAvailableException(TimeSlotNotAvailableException ex) {
//
//        return ResponseEntity.status(400).body(ex.getMessage());
//    }
//}

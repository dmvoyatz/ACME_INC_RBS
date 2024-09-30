package ACMELab.Inc.Rendezvous.RBS.exceptions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleBookingNotFoundException(BookingNotFoundException ex, WebRequest request) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Booking with ID: " + ex.getMessage() + " not found."
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
      
    }

    @ExceptionHandler(IllegalBookingDeletionException.class)
    public ResponseEntity<String> handleIllegalBookingDeletionException(IllegalBookingDeletionException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleRoomNotFoundException(RoomNotFoundException ex, WebRequest request) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
      
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }




    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomErrorResponse> handleInvalidDateFormat(MethodArgumentTypeMismatchException ex, WebRequest request) {
        // Check if the exception is related to LocalDate
        if (ex.getRequiredType() != null && ex.getRequiredType().equals(LocalDate.class)) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid date format: " + ex.getValue() + ". Please use 'yyyy-MM-dd' format."
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ExceptionHandler(BookingConflictException.class)
    public ResponseEntity<CustomErrorResponse> handleBookingConflictException(BookingConflictException ex, WebRequest request) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidBookingTimeException.class)
    public ResponseEntity<CustomErrorResponse> handleInvalidBookingTimeException(InvalidBookingTimeException ex, WebRequest request) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
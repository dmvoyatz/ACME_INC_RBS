package ACMELab.Inc.Rendezvous.RBS.exceptions;

public class BookingConflictException extends RuntimeException {
    public BookingConflictException(String message) {
        super(message);
    }
}
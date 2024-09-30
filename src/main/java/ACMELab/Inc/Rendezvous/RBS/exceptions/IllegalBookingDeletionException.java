package ACMELab.Inc.Rendezvous.RBS.exceptions;

public class IllegalBookingDeletionException extends RuntimeException {
    public IllegalBookingDeletionException(String message) {
        super(message);
    }
    public IllegalBookingDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
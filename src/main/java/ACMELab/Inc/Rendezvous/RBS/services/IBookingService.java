package ACMELab.Inc.Rendezvous.RBS.services;

import ACMELab.Inc.Rendezvous.RBS.entities.Booking;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.ResponseEntity;


public interface IBookingService {
    
    ResponseEntity<List<Booking>> getBookingsForRoomOnDate(Long roomId, LocalDate date);

    ResponseEntity<String> deleteBooking(Long bookingId);

    ResponseEntity<String> createBooking(Long roomId, String employeeEmail, LocalDate bookingDate, LocalTime startTime, LocalTime endTime);

}


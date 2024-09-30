package ACMELab.Inc.Rendezvous.RBS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ACMELab.Inc.Rendezvous.RBS.dtos.BookingRequest;
import ACMELab.Inc.Rendezvous.RBS.entities.Booking;
import ACMELab.Inc.Rendezvous.RBS.services.IBookingService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

     /**
     * Handles GET requests to fetch the list of all bookings for specific room and date.
     * @return a list of booking entities
     */
    @GetMapping("/room/{roomId}/date/{date}")
    public ResponseEntity<List<Booking>> getRoomBookings(
        @PathVariable Long roomId, 
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return bookingService.getBookingsForRoomOnDate(roomId, date);
    }

    /**
     * Handles DELETE requests to delete a specific.
     * @return success if booking found and deleted else error
     */
    @DeleteMapping("/booking/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long bookingId) {
        return bookingService.deleteBooking(bookingId);
    }

    /**
     * Handles POST requests to create a new booking.
     *  * @param booking the booking entity to be saved
     * @return success message
     */
    @PostMapping("/new-booking")
    public ResponseEntity<String> createBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingService.createBooking(
            bookingRequest.getRoomId(),
            bookingRequest.getEmployeeEmail(),
            bookingRequest.getBookingDate(),
            bookingRequest.getStartTime(),
            bookingRequest.getEndTime()
        );
    }

}

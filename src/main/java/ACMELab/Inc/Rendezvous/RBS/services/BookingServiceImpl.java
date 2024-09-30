package ACMELab.Inc.Rendezvous.RBS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ACMELab.Inc.Rendezvous.RBS.entities.Booking;
import ACMELab.Inc.Rendezvous.RBS.entities.Room;
import ACMELab.Inc.Rendezvous.RBS.exceptions.BookingNotFoundException;
import ACMELab.Inc.Rendezvous.RBS.exceptions.IllegalBookingDeletionException;
import ACMELab.Inc.Rendezvous.RBS.exceptions.RoomNotFoundException;
import ACMELab.Inc.Rendezvous.RBS.exceptions.InvalidBookingTimeException;
import ACMELab.Inc.Rendezvous.RBS.exceptions.BookingConflictException;
import ACMELab.Inc.Rendezvous.RBS.repositories.BookingRepository;
import ACMELab.Inc.Rendezvous.RBS.repositories.RoomRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class BookingServiceImpl implements IBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public ResponseEntity<List<Booking>> getBookingsForRoomOnDate(Long roomId, LocalDate date) {
        // Check if the room exists
        if (!roomRepository.existsById(roomId)) {
            throw new RoomNotFoundException("Room with ID: " + roomId + " not found.");
        }
       // Fetch bookings for the room on the specified date
       List<Booking> bookings = bookingRepository.findByRoomIdAndBookingDate(roomId, date);
       return ResponseEntity.ok(bookings);
       
    }

    public ResponseEntity<String> deleteBooking(Long bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
    
        // Check if booking exists
        if (optionalBooking.isEmpty()) {
            throw new BookingNotFoundException("Booking with ID: " + bookingId + " not found.");
        }

        Booking booking = optionalBooking.get();
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        // Check if the booking date is in the past or it is today but endTime after currentTime     
        if (booking.getBookingDate().isBefore(today) || 
        (booking.getBookingDate().isEqual(today) && currentTime.isAfter(booking.getEndTime()) )) {
            throw new IllegalBookingDeletionException("Cannot cancel booking with ID: " + bookingId + " because it already happened.");
        }
        // Check if the booking is currently in progress
        if (booking.getBookingDate().isEqual(today) && currentTime.isAfter(booking.getStartTime()) && currentTime.isBefore(booking.getEndTime())) {
            throw new IllegalBookingDeletionException("Cannot cancel booking with ID: " + bookingId + " because it is in progress.");
        }

        // Proceed with booking deletion
        bookingRepository.delete(booking);
        return ResponseEntity.ok("Booking with ID: " + bookingId + " deleted successfully.");
    }

    @Override
    public ResponseEntity<String> createBooking(Long roomId, String employeeEmail, LocalDate bookingDate, LocalTime startTime, LocalTime endTime) {
        
        // Validate inputs
        validateBookingInputs(roomId, employeeEmail, bookingDate, startTime, endTime);

        // Validate room existence
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room with ID: " + roomId + " not found."));

        // Validate time duration (minimum 1 hour and must be in whole-hour multiples)
        Duration duration = Duration.between(startTime, endTime);
        if (duration.toHours() < 1 || duration.toMinutes() % 60 != 0) {
            throw new InvalidBookingTimeException("Booking Conflict: Booking duration must be at least 1 hour or consecutive multiples of 1 hour.");
        }

        // Validate for overlapping bookings
        List<Booking> existingBookings = bookingRepository.findByRoomIdAndBookingDate(roomId, bookingDate);
        for (Booking booking : existingBookings) {
            if (isTimeOverlapping(startTime, endTime, booking.getStartTime(), booking.getEndTime())) {
                throw new BookingConflictException("Booking Conflict: The requested time slot overlaps with an existing booking.");
            }
        }

        // Create and save the booking
        Booking newBooking = Booking.builder()
                .room(room)
                .employeeEmail(employeeEmail)
                .bookingDate(bookingDate)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        bookingRepository.save(newBooking);

        return ResponseEntity.ok("Booking successfully created.");
    }

    // Helper function to check if two time intervals overlap
    private boolean isTimeOverlapping(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    private void validateBookingInputs(Long roomId, String employeeEmail, LocalDate bookingDate, LocalTime startTime, LocalTime endTime) {
        List<String> missingFields = new ArrayList<>();

        if (roomId == null) {
            missingFields.add("roomId");
        }
        if (employeeEmail == null || employeeEmail.isEmpty()) {
            missingFields.add("employeeEmail");
        }
        if (bookingDate == null) {
            missingFields.add("bookingDate");
        }
        if (startTime == null) {
            missingFields.add("startTime");
        }
        if (endTime == null) {
            missingFields.add("endTime");
        }

        if (!missingFields.isEmpty()) {
            throw new IllegalArgumentException("Missing or invalid fields: " + String.join(", ", missingFields));
        }
    }



}

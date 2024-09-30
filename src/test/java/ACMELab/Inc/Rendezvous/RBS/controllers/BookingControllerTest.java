package ACMELab.Inc.Rendezvous.RBS.controllers;

import ACMELab.Inc.Rendezvous.RBS.dtos.BookingRequest;
import ACMELab.Inc.Rendezvous.RBS.entities.Booking;
import ACMELab.Inc.Rendezvous.RBS.entities.Room;
import ACMELab.Inc.Rendezvous.RBS.services.IBookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    //testCreateBooking
    @Test
    public void testCreateBooking() throws Exception {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setRoomId(1L);
        bookingRequest.setEmployeeEmail("test_user_email@example.com");
        bookingRequest.setBookingDate(LocalDate.now());
        bookingRequest.setStartTime(LocalTime.of(9, 0));
        bookingRequest.setEndTime(LocalTime.of(10, 0));

        when(bookingService.createBooking(any(Long.class), any(String.class), any(LocalDate.class), any(LocalTime.class), any(LocalTime.class)))
            .thenReturn(new ResponseEntity<>("Booking created successfully", HttpStatus.CREATED));

        String bookingRequestJson = "{"
            + "\"roomId\": 1,"
            + "\"employeeEmail\": \"test_employee_username@acme.com\","
            + "\"bookingDate\": \"" + LocalDate.now() + "\","
            + "\"startTime\": \"09:00\","
            + "\"endTime\": \"10:00\""
            + "}";

        mockMvc.perform(post("/api/bookings/new-booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookingRequestJson))
            .andExpect(status().isCreated())
            .andExpect(content().string("Booking created successfully"));
    }

    //testGetRoomBookings
    @Test
    public void testGetRoomBookings() throws Exception {
        List<Booking> bookings = new ArrayList<>();
        // Add mock room data
        Room room = Room.builder()
                .id(1L)
                .name("Test Conference Room A")
                .code("TCR-A")
                .build();
        // Add mock booking data
        Booking booking = Booking.builder()
        .room(room)
        .employeeEmail("test_employee_username@acme.com")
        .bookingDate(LocalDate.now())
        .startTime(LocalTime.of(9, 0))
        .endTime(LocalTime.of(10, 0))
        .build();
        bookings.add(booking);

        when(bookingService.getBookingsForRoomOnDate(any(Long.class), any(LocalDate.class)))
            .thenReturn(new ResponseEntity<>(bookings, HttpStatus.OK));

        mockMvc.perform(get("/api/bookings/room/1/date/" + LocalDate.now().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].room.id").value(1))
            .andExpect(jsonPath("$[0].bookingDate[0]").value(LocalDate.now().getYear()))
            .andExpect(jsonPath("$[0].bookingDate[1]").value(LocalDate.now().getMonthValue()))
            .andExpect(jsonPath("$[0].bookingDate[2]").value(LocalDate.now().getDayOfMonth()));

    }

    //testDeleteBooking
    @Test
    public void testDeleteBooking() throws Exception {
        when(bookingService.deleteBooking(any(Long.class)))
            .thenReturn(new ResponseEntity<>("Booking deleted successfully", HttpStatus.OK));

        mockMvc.perform(delete("/api/bookings/booking/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Booking deleted successfully"));
    }

    
}

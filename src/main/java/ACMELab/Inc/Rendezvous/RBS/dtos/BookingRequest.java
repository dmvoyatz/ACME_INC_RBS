package ACMELab.Inc.Rendezvous.RBS.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private Long roomId;
    private String employeeEmail;
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
}

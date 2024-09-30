package ACMELab.Inc.Rendezvous.RBS.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ACMELab.Inc.Rendezvous.RBS.entities.Booking;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRoomIdAndBookingDate(Long roomId, LocalDate bookingDate);

}
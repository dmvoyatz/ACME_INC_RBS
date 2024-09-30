-- Create the Room table
CREATE TABLE Room (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    code VARCHAR(24) NOT NULL UNIQUE
);

-- Create the Booking table
CREATE TABLE Booking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id BIGINT NOT NULL,
    employee_email VARCHAR(255) NOT NULL,
    booking_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    
    -- Foreign key constraint
    FOREIGN KEY (room_id) REFERENCES Room(id)
    
    -- Unique constraint to prevent overlapping bookings for the same room on the same day (if we want to manage it on database scope)
    -- UNIQUE (room_id, booking_date, start_time, end_time)
);

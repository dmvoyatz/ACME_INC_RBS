-- Insert rooms
INSERT INTO Room (name, code) VALUES ('Main Conference Room', 'MCR');
INSERT INTO Room (name, code) VALUES ('Secondary Conference Room', 'SCR');
INSERT INTO Room (name, code) VALUES ('Third Floor Meeting Room A', 'TRD_FMR_A');
INSERT INTO Room (name, code) VALUES ('Third Floor Meeting Room B', 'TRD_FMR_B');
INSERT INTO Room (name, code) VALUES ('Third Floor Meeting Room C', 'TRD_FMR_C');
INSERT INTO Room (name, code) VALUES ('Second Floor Booth A', 'SND_FB_A');
INSERT INTO Room (name, code) VALUES ('Second Floor Booth B', 'SND_FB_B');

-- Insert bookings 
INSERT INTO Booking (room_id, employee_email, booking_date, start_time, end_time) VALUES (1, 'employee1@harborlab.com', '2024-09-26', '09:00', '10:00');
INSERT INTO Booking (room_id, employee_email, booking_date, start_time, end_time) VALUES (1, 'employee2@harborlab.com', '2024-09-26', '11:00', '12:00');
INSERT INTO Booking (room_id, employee_email, booking_date, start_time, end_time) VALUES (2, 'employee3@harborlab.com', '2024-09-26', '13:00', '14:00');
INSERT INTO Booking (room_id, employee_email, booking_date, start_time, end_time) VALUES (3, 'employee4@harborlab.com', '2024-09-26', '15:00', '16:00');
INSERT INTO Booking (room_id, employee_email, booking_date, start_time, end_time) VALUES (4, 'employee5@harborlab.com', '2024-09-28', '10:00', '12:00');
INSERT INTO Booking (room_id, employee_email, booking_date, start_time, end_time) VALUES (5, 'employee6@harborlab.com', '2024-09-29', '08:00', '09:00');


INSERT INTO Booking (room_id, employee_email, booking_date, start_time, end_time) VALUES (5, 'employee6@harborlab.com', '2024-09-27', '15:00', '19:00');
INSERT INTO Booking (room_id, employee_email, booking_date, start_time, end_time) VALUES (5, 'employee6@harborlab.com', '2024-09-27', '14:00', '15:00');

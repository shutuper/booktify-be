package org.qqsucc.booktify.notification.service;

import org.qqsucc.booktify.appointment.repository.entity.Appointment;

public interface NotificationService {

	void sendMasterInvitation(String maserEmail, String inviteToken, String salon);

	void sendSmsOtpCode(String phone, String otpCode);

	void sendAppointmentBooked(Appointment appointment);

	void sendAppointmentReminder(Appointment appointment);

}

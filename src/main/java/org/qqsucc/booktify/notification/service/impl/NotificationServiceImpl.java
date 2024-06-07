package org.qqsucc.booktify.notification.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.qqsucc.booktify.appointment.repository.entity.Appointment;
import org.qqsucc.booktify.common.util.VelocityUtils;
import org.qqsucc.booktify.email.service.EmailService;
import org.qqsucc.booktify.email.service.domain.EmailDto;
import org.qqsucc.booktify.notification.service.NotificationConstants;
import org.qqsucc.booktify.notification.service.NotificationService;
import org.qqsucc.booktify.sms.service.SmsService;
import org.qqsucc.booktify.user.repository.entity.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class NotificationServiceImpl implements NotificationService {

	NotificationConstants constants;
	VelocityUtils velocityUtils;
	EmailService emailService;
	SmsService smsService;

	@Override
	public void sendMasterInvitation(String maserEmail, String inviteToken, String salon) {
		log.info("SENDING_EMAIL: You have been invited to join {} salon, inviteToken={}", salon, inviteToken);
		emailService.sendEmail(EmailDto.builder()
				.subject("You have been invited to join Booktify!")
				.content(velocityUtils.mergeTemplate(
						constants.MASTER_INVITATION_EMAIL_TEMPLATE,
						Map.of(
								"inviteUrl", constants.LINK + "/sign-up?inviteToken=" + inviteToken,
								"salon", salon
						)
				))
				.from(constants.SENT_FROM_EMAIL)
				.to(maserEmail)
				.build());
	}

	@Async
	@Override
	public void sendSmsOtpCode(String phone, String otpCode) {
		String message = "Booktify otp code: " + otpCode;
		log.info("SENDING_SMS: {}", message);
		smsService.sendSms(phone, message);
	}

	@Async
	@Override
	public void sendAppointmentBooked(Appointment appointment) {
		User master = appointment.getProcedure().getMaster();
		String appointmentDate = LocalDateTime
				.ofInstant(appointment.getStartDate(), ZoneId.of("Europe/Kiev"))
				.format(DateTimeFormatter.ofPattern("MMM dd, EEE HH:mm").withLocale(Locale.ENGLISH));

		smsService.sendSms(appointment.getClient().getPhone(), String.format(
				"Hi, your appointment is booked on %s with %s %s at %s - Booktify",
				appointmentDate,
				master.getFirstname(),
				master.getLastname(),
				appointment.getProcedure().getMaster().getSalonMaster().getSalon().getTitle()
		));
	}

	@Async
	@Override
	public void sendAppointmentReminder(Appointment appointment) {
		User master = appointment.getProcedure().getMaster();
		String appointmentDate = LocalDateTime
				.ofInstant(appointment.getStartDate(), ZoneId.of("Europe/Kiev"))
				.format(DateTimeFormatter.ofPattern("MMM dd, EEE HH:mm").withLocale(Locale.ENGLISH));

		smsService.sendSms(appointment.getClient().getPhone(),
				String.format("Hi, it's a reminder that you have appointment booked on %s with %s %s at %s - Booktify",
						appointmentDate,
						master.getFirstname(),
						master.getLastname(),
						appointment.getProcedure().getMaster().getSalonMaster().getSalon().getTitle()
				)
		);
	}
}

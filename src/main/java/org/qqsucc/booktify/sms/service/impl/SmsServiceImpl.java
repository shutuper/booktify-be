package org.qqsucc.booktify.sms.service.impl;

import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.qqsucc.booktify.notification.service.NotificationConstants;
import org.qqsucc.booktify.sms.service.SmsService;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class SmsServiceImpl implements SmsService {

	NotificationConstants notificationConstants;

	@Override
	public void sendSms(String phoneNumber, String message) {
		Message.creator(
				new com.twilio.type.PhoneNumber("+" + phoneNumber), // to
				new com.twilio.type.PhoneNumber(notificationConstants.SENT_FROM_PHONE), // from
				message // sms content
		).create();
	}
}

package org.qqsucc.booktify.sms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.qqsucc.booktify.sms.service.SmsService;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class SmsServiceImpl implements SmsService {


	@Override
	public void sendSms(String phoneNumber, String message) {
		// todo add logic to send sms
		log.info("SMS_CODE: {}", message);
	}
}

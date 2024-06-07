package org.qqsucc.booktify.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PUBLIC;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = PUBLIC)
public class NotificationConstants {

	// GENERAL
	@Value("${notifications.link}")
	String LINK;

	// EMAIL
	@Value("${notifications.email.from}")
	String SENT_FROM_EMAIL;

	@Value("${templates.email.master_invitation}")
	String MASTER_INVITATION_EMAIL_TEMPLATE;

	// SMS
	@Value("${notifications.sms.from}")
	String SENT_FROM_PHONE;

}

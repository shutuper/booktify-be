package org.qqsucc.booktify.email.service;

import org.qqsucc.booktify.email.service.domain.EmailDto;

public interface EmailService {

	void sendEmail(EmailDto emailDto);

}

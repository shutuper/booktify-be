package org.qqsucc.booktify.email.service.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.qqsucc.booktify.common.exception.BusinessException;
import org.qqsucc.booktify.email.service.EmailService;
import org.qqsucc.booktify.email.service.domain.EmailDto;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class EmailServiceImpl implements EmailService {

	SendGrid sendGrid;

	@Override
	public void sendEmail(EmailDto emailDto) {
		Email from = new Email(emailDto.getFrom(), "Booktify");
		String subject = emailDto.getSubject();
		Email to = new Email(emailDto.getTo());
		Content content = new Content("text/html", emailDto.getContent());
		Mail mail = new Mail(from, subject, to, content);

		Request request = new Request();
		request.setMethod(Method.POST);
		request.setEndpoint("mail/send");

		try {

			request.setBody(mail.build());
			sendGrid.api(request);

		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
			throw new BusinessException(ex.getMessage());
		}
	}
}

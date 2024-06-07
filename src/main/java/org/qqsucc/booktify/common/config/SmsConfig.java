package org.qqsucc.booktify.common.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {

	@NonFinal
	@Value("${twilio.account-sid}")
	String accountSid;

	@NonFinal
	@Value("${twilio.auth-token}")
	String authToken;

	@PostConstruct
	public void init() {
		Twilio.init(accountSid, authToken);
	}

}

package org.qqsucc.booktify.common.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

	@Value("${sendgrid.api-key}")
	String sendGridApiKey;

	@Bean
	public SendGrid sendGrid() {
		return new SendGrid(sendGridApiKey);
	}

}

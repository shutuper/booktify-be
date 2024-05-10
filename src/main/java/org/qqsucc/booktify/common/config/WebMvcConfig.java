package org.qqsucc.booktify.common.config;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executor;

import static lombok.AccessLevel.PRIVATE;

@Configuration
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class WebMvcConfig implements WebMvcConfigurer {

    Executor taskExecutor;

	public WebMvcConfig(@Qualifier("taskExecutor") Executor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	@Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor((AsyncTaskExecutor) taskExecutor);
    }

}

package kektor.innowise.gallery.authentication.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.task.ThreadPoolTaskSchedulerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ErrorHandler;


@Slf4j
@EnableScheduling
@Configuration
public class AsyncConfig {

    public static final String SCHEDULED_METHOD_EXCEPTION_MESSAGE = "Exception while invoking scheduled method. Exception:[{}] Message:[{}]";

    @Bean
    public ThreadPoolTaskSchedulerCustomizer schedulerCustomizer() {
        return taskScheduler ->
                taskScheduler.setErrorHandler(schedulingExceptionHandler());
    }

    @Bean
    public ErrorHandler schedulingExceptionHandler() {
        return ex ->
                log.error(SCHEDULED_METHOD_EXCEPTION_MESSAGE,
                        ex.getClass().getSimpleName(), ex.getMessage());
    }

}




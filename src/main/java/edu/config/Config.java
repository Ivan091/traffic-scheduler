package edu.config;

import edu.model.intensity.*;
import edu.model.scheduling.NextMomentRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.*;


@Configuration
@Slf4j
public class Config {

    @Bean
    public Supplier<LocalDateTime> localDateSupplier() {
        return LocalDateTime::now;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        return taskScheduler;
    }

    @Bean
    public Function<List<Intensity>, OneHourIntensities> currentHourIntensitiesFactory(BiConsumer<SingleOriginIntensities, LocalDateTime> handler,
                                                                                       NextMomentRule nextMomentRule) {
        return (intensities) -> OneHourIntensities.of(intensities, nextMomentRule, handler);
    }
}

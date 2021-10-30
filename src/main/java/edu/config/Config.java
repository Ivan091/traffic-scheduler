package edu.config;

import edu.model.intensity.CurrentHourIntensities;
import edu.model.intensity.Intensity;
import edu.model.scheduling.NextMomentRule;
import edu.repo.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;


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
    public Function<List<Intensity>, CurrentHourIntensities> currentHourIntensitiesFactory(Supplier<LocalDateTime> localDateSupplier,
                                                                                           TaskScheduler taskScheduler, OrderRepo orderRepo) {
        return (intensities) -> CurrentHourIntensities.of(intensities, new NextMomentRule(), localDateSupplier, taskScheduler, orderRepo);
    }
}

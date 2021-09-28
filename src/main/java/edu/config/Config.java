package edu.config;

import edu.model.scheduler.CallbackTask;
import edu.repository.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;


@Configuration
public class Config {

    private final Random random = new Random();

    @Bean
    public Supplier<LocalDateTime> localDateSupplier() {
        return LocalDateTime::now;
    }

    @Bean
    public Timer timer() {
        return new Timer();
    }

    @Bean
    public Supplier<Order> orderSupplier(Supplier<LocalDateTime> localDateSupplier) {
        return () -> new Order(random.nextInt(10), random.nextInt(10), random.nextInt(10), Timestamp.valueOf(localDateSupplier.get()));
    }

    @Bean
    public Function<Runnable, TimerTask> intervalTimerFunction() {
        return CallbackTask::new;
    }
}

package edu.config;

import edu.model.Order;
import edu.model.scheduler.CallbackTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Timer;
import java.util.function.Function;
import java.util.function.Supplier;


@Configuration
public class Config {

    private final Random random = new Random();

    @Bean
    public Timer timer() {
        return new Timer();
    }

    @Bean
    public Supplier<Order> orderSupplier() {
        return () -> new Order(random.nextInt(10), random.nextInt(10), random.nextInt(10), Timestamp.valueOf(LocalDateTime.now()));
    }

    @Bean
    public Function<Runnable, CallbackTask> intervalTimerFunction() {
        return CallbackTask::new;
    }
}

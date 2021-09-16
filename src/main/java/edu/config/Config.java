package edu.config;

import edu.model.CallbackTask;
import edu.model.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Timer;
import java.util.function.*;


@Configuration
public class Config {

    private final Random random = new Random();

    @Bean
    public Timer timer(){
        return new Timer();
    }

    @Bean
    public Supplier<Order> orderSupplier(){
        return () -> new Order(0, random.nextInt(10), random.nextInt(10), LocalDateTime.now());
    }

    @Bean
    public Function<Runnable, CallbackTask> intervalTimerFunction(){
        return CallbackTask::new;
    }
}

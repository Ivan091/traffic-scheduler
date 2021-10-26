package edu.config;

import edu.model.Order;
import edu.model.Path;
import edu.repository.OrderRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.Supplier;


@Configuration
public class Config {

    @Bean
    public Supplier<LocalDateTime> localDateSupplier() {
        return LocalDateTime::now;
    }

    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(1);
    }

    @Bean
    public Timer timer() {
        return new Timer();
    }

    @Bean
    public Supplier<Order> orderSupplier(DelayProperties delayProperties, Supplier<Path> pathSupplier, Supplier<LocalDateTime> localDateSupplier) {
        return () -> new Order(pathSupplier.get(), RandomUtils.nextInt(1, delayProperties.maxSeatsCount), Timestamp.valueOf(localDateSupplier.get()));
    }

    @Bean
    public Runnable saveOrder(OrderRepository orderRepository, Supplier<Order> orderSupplier) {
        return () -> orderRepository.save(orderSupplier.get());
    }
}

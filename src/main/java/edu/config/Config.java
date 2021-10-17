package edu.config;

import edu.model.Order;
import edu.model.Path;
import edu.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.Supplier;


@Configuration
public class Config {

    private final Random random = new Random();

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
    public Supplier<Order> orderSupplier(Supplier<Path> pathSupplier, Supplier<LocalDateTime> localDateSupplier) {
        return () -> new Order(pathSupplier.get(), random.nextInt(10), Timestamp.valueOf(localDateSupplier.get()));
    }

    @Bean
    public Runnable saveOrder(OrderRepository orderRepository, Supplier<Order> orderSupplier) {
        return () -> orderRepository.save(orderSupplier.get());
    }
}

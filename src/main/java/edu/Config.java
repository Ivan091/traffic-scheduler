package edu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Timer;
import java.util.function.Supplier;


@Configuration
public class Config {

    private int idCounter = 1;

    @Bean
    public Timer timer() {
        return new Timer();
    }

    @Bean
    public Supplier<Order> orderGenerator() {
        var rand = new Random();
        return () -> new Order(idCounter++, rand.nextInt(5), rand.nextInt(10), LocalDateTime.now());
    }
}

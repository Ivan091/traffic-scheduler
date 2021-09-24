package edu.model;

import edu.config.YamlPropertySourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.Timer;
import java.util.function.Function;
import java.util.function.Supplier;


@Service
@PropertySource(value = "classpath:order.yml", factory = YamlPropertySourceFactory.class)
public class OrderScheduler implements Scheduler {

    private final Timer timer;

    private final Supplier<Order> orderSupplier;

    private final Function<Runnable, CallbackTask> intervalTimerFunction;

    private final Double discrepancy;

    public OrderScheduler(@Value("${order.delta}") Double discrepancy,
                          Timer timer, Supplier<Order> orderSupplier, Function<Runnable, CallbackTask> intervalTimerFunction) {
        this.discrepancy = discrepancy;
        this.timer = timer;
        this.orderSupplier = orderSupplier;
        this.intervalTimerFunction = intervalTimerFunction;
    }

    @Override
    public void run() {
        System.out.println(discrepancy);
        System.out.println(orderSupplier.get());
        timer.schedule(intervalTimerFunction.apply(this), new Random().nextInt(10000));
    }
}

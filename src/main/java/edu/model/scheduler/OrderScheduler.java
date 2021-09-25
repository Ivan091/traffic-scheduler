package edu.model.scheduler;

import edu.config.YamlPropertySourceFactory;
import edu.model.Order;
import edu.repository.OrderRepository;
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

    private final OrderRepository orderRepository;

    public OrderScheduler(@Value("${order.delta}") Double discrepancy,
                          OrderRepository orderRepository,
                          Timer timer, Supplier<Order> orderSupplier, Function<Runnable, CallbackTask> intervalTimerFunction) {
        this.discrepancy = discrepancy;
        this.orderRepository = orderRepository;
        this.timer = timer;
        this.orderSupplier = orderSupplier;
        this.intervalTimerFunction = intervalTimerFunction;
    }

    @Override
    public void run() {
        orderRepository.save(orderSupplier.get());
        timer.schedule(intervalTimerFunction.apply(this), new Random().nextInt(10000) + 1000);
    }
}

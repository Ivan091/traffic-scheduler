package edu.model.scheduler;

import edu.model.Order;
import edu.model.scheduler.delay.Doorman;
import edu.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;
import java.util.function.Supplier;


@Service
public class OrderScheduler implements Scheduler {

    private final Timer timer;

    private final Supplier<Order> orderSupplier;

    private final Function<Runnable, TimerTask> intervalTimerFunction;

    private final Doorman doorman;

    private final OrderRepository orderRepository;

    public OrderScheduler(OrderRepository orderRepository,
                          Timer timer,
                          Supplier<Order> orderSupplier,
                          Function<Runnable, TimerTask> intervalTimerFunction,
                          Doorman doorman
    ) {
        this.orderRepository = orderRepository;
        this.timer = timer;
        this.orderSupplier = orderSupplier;
        this.intervalTimerFunction = intervalTimerFunction;
        this.doorman = doorman;
    }

    @Override
    public void run() {
        orderRepository.save(orderSupplier.get());
        timer.schedule(intervalTimerFunction.apply(this), doorman.choose());
    }
}

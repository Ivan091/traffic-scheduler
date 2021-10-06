package edu.model.scheduler;

import edu.model.delay.Doorman;
import edu.repository.OrderRepository;
import edu.repository.entity.Order;
import org.springframework.stereotype.Service;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;
import java.util.function.Supplier;


@Service
public final class OrderScheduler implements Scheduler {

    private final Timer timer;

    private final Supplier<Order> orderSupplier;

    private final Function<Runnable, TimerTask> intervalTimerFunction;

    private final Doorman doorman;

    private final OrderRepository orderRepository;

    public OrderScheduler(Timer timer,
                          Supplier<Order> orderSupplier,
                          Function<Runnable, TimerTask> intervalTimerFunction,
                          Doorman doorman,
                          OrderRepository orderRepository) {
        this.timer = timer;
        this.orderSupplier = orderSupplier;
        this.intervalTimerFunction = intervalTimerFunction;
        this.doorman = doorman;
        this.orderRepository = orderRepository;
    }

    @Override
    public void run() {
        orderRepository.save(orderSupplier.get());
        timer.schedule(intervalTimerFunction.apply(this), doorman.choose().inMilliseconds());
    }

    @Override
    public String toString() {
        return "OrderScheduler[" +
                "timer=" + timer + ", " +
                "orderSupplier=" + orderSupplier + ", " +
                "intervalTimerFunction=" + intervalTimerFunction + ", " +
                "doorman=" + doorman + ", " +
                "orderRepository=" + orderRepository + ']';
    }
}

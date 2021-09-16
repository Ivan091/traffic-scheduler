package edu.model;

import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.Timer;
import java.util.function.*;


@Service
public class OrderScheduler implements Scheduler {

    private final Timer timer;

    private final Supplier<Order> orderSupplier;

    private final Function<Runnable, CallbackTask> intervalTimerFunction;

    public OrderScheduler(Timer timer, Supplier<Order> orderSupplier, Function<Runnable, CallbackTask> intervalTimerFunction) {
        this.timer = timer;
        this.orderSupplier = orderSupplier;
        this.intervalTimerFunction = intervalTimerFunction;
    }

    @Override
    public void run() {
        System.out.println(orderSupplier.get());
        timer.schedule(intervalTimerFunction.apply(this), new Random().nextInt(10000));
    }
}

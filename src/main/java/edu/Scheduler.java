package edu;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.function.Supplier;


@Service
public class Scheduler {

    private final Timer timer;

    private final Supplier<Order> orderSupplier;

    private final Random random = new Random();

    public Scheduler(Timer timer, Supplier<Order> orderSupplier) {
        this.timer = timer;
        this.orderSupplier = orderSupplier;
    }

    public void run() {
        timer.schedule(buildTask(), 1000);
    }

    private TimerTask buildTask() {
        return new TimerTask() {
            @Override
            public void run() {
                System.out.println(orderSupplier.get());
                var delay = random.nextInt(10000) + 1000;
                System.out.printf("delay = %s ms\n", delay);
                timer.schedule(buildTask(), delay);
            }
        };
    }
}

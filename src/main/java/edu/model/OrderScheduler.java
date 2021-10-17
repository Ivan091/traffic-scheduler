package edu.model;

import edu.model.delay.Delay;
import org.springframework.stereotype.Service;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Service
public final class OrderScheduler implements Runnable {

    private final ScheduledExecutorService executor;

    private final Runnable saveOrder;

    private final Delay dayDependentDelay;

    public OrderScheduler(ScheduledExecutorService executor,
                          Runnable saveOrder,
                          Delay dayDependentDelay) {
        this.executor = executor;
        this.saveOrder = saveOrder;
        this.dayDependentDelay = dayDependentDelay;
    }

    @Override
    public void run() {
        saveOrder.run();
        executor.schedule(this, dayDependentDelay.inMilliseconds(), TimeUnit.MILLISECONDS);
    }
}

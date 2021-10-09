package edu.model.scheduler;

import edu.model.delay.DayDependentDelay;
import org.springframework.stereotype.Service;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Service
public final class OrderScheduler implements Scheduler {

    private final ScheduledExecutorService executor;

    private final SaveOrder saveOrder;

    private final DayDependentDelay dayDependentDelay;

    public OrderScheduler(ScheduledExecutorService executor,
                          SaveOrder saveOrder,
                          DayDependentDelay dayDependentDelay) {
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

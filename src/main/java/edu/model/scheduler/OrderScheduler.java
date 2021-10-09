package edu.model.scheduler;

import edu.model.delay.DayDependentDelay;
import org.springframework.stereotype.Service;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Service
public final class OrderScheduler implements Scheduler {

    private final ScheduledExecutorService taskScheduler;

    private final SaveOrder saveOrder;

    private final DayDependentDelay dayDependentDelay;

    public OrderScheduler(ScheduledExecutorService taskScheduler,
                          SaveOrder saveOrder,
                          DayDependentDelay dayDependentDelay) {
        this.taskScheduler = taskScheduler;
        this.saveOrder = saveOrder;
        this.dayDependentDelay = dayDependentDelay;
    }

    @Override
    public void run() {
        saveOrder.run();
        taskScheduler.schedule(this, dayDependentDelay.inMilliseconds(), TimeUnit.MILLISECONDS);
    }
}

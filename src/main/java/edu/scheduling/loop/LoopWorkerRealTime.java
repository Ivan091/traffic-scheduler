package edu.scheduling.loop;

import edu.scheduling.SchedulingHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import java.sql.Timestamp;


@AllArgsConstructor
@Slf4j
public class LoopWorkerRealTime implements Worker {

    private final Integer origin;

    private final SchedulingHandler handler;

    private final TaskScheduler taskScheduler;

    private final LoopWorkerService loopWorkerService;

    @Override
    public void run() {
        var planTime = loopWorkerService.planTime(origin, handler);
        taskScheduler.schedule(this, Timestamp.valueOf(planTime));
    }
}

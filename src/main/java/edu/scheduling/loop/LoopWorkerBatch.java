package edu.scheduling.loop;

import edu.model.TimeRange;
import edu.scheduling.SchedulingHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;


@Slf4j
@AllArgsConstructor
public class LoopWorkerBatch implements Worker {

    private final Integer origin;

    private final SchedulingHandler handler;

    private final TaskExecutor taskExecutor;

    private final LoopWorkerService loopWorkerService;

    private TimeRange timeRange;

    @Override
    public void run() {
        if (timeRange.isAscending()) {
            var planTime = loopWorkerService.planTime(origin, handler, timeRange.getBegin());
            timeRange = timeRange.withBegin(planTime);
            taskExecutor.execute(this);
        }
    }
}

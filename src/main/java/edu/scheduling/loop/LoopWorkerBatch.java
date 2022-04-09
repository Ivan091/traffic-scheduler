package edu.scheduling.loop;

import edu.model.TimeRange;
import edu.model.intensity.SchedulingIntensities;
import edu.scheduling.SchedulingHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import java.time.LocalDateTime;


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
            loopWorkerService.schedule(origin, this, timeRange.getBegin());
        }
    }

    @Override
    public void scheduleNext(SchedulingIntensities intensities, LocalDateTime time) {
        timeRange = timeRange.withBegin(time);
        taskExecutor.execute(() -> handler.handle(intensities, time));
        taskExecutor.execute(this);
    }
}

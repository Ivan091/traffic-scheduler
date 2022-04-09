package edu.scheduling.loop;

import edu.model.intensity.SchedulingIntensities;
import edu.scheduling.SchedulingHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@AllArgsConstructor
@Slf4j
public class LoopWorkerRealTime implements Worker {

    private final Integer origin;

    private final SchedulingHandler handler;

    private final TaskScheduler taskScheduler;

    private final LoopWorkerService loopWorkerService;

    @Override
    public void run() {
        loopWorkerService.schedule(origin, this);
    }

    @Override
    public void scheduleNext(SchedulingIntensities schedulingIntensities, LocalDateTime time) {
        var timestamp = Timestamp.valueOf(time);
        taskScheduler.schedule(() -> handler.handle(schedulingIntensities, time), timestamp);
        taskScheduler.schedule(this, timestamp);
    }
}

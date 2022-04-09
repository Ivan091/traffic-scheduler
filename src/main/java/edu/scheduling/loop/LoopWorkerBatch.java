package edu.scheduling.loop;

import edu.model.TimeRange;
import edu.model.intensity.SchedulingIntensities;
import edu.scheduling.SchedulingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;


@Service
public class LoopWorkerBatch implements WorkerBatch {

    @Qualifier("CSVFileHandler")
    @Autowired
    private SchedulingHandler handler;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private LoopWorkerService loopWorkerService;

    @Override
    public void start(Integer origin, TimeRange range) {
        Scheduling adapter = (intensities, time) -> scheduleNext(intensities, range.withCurrent(time));
        if (range.isAscending()) {
            loopWorkerService.schedule(origin, adapter, range.getCurrent());
        }
    }

    @Override
    public void scheduleNext(SchedulingIntensities intensities, TimeRange time) {
        taskExecutor.execute(() -> handler.handle(intensities, time.getCurrent()));
        taskExecutor.execute(() -> start(intensities.getOrigin(), time));
    }
}

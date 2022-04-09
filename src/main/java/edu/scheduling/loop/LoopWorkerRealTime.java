package edu.scheduling.loop;

import edu.model.intensity.SchedulingIntensities;
import edu.scheduling.SchedulingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Service
public class LoopWorkerRealTime implements Worker {

    @Qualifier("DBHandler")
    @Autowired
    SchedulingHandler handler;

    @Autowired
    TaskScheduler taskScheduler;

    @Autowired
    LoopWorkerService loopWorkerService;

    @Override
    public void start(Integer origin) {
        loopWorkerService.schedule(origin, this);
    }

    @Override
    public void scheduleNext(SchedulingIntensities schedulingIntensities, LocalDateTime time) {
        var timestamp = Timestamp.valueOf(time);
        taskScheduler.schedule(() -> handler.handle(schedulingIntensities, time), timestamp);
        taskScheduler.schedule(() -> start(schedulingIntensities.getOrigin()), timestamp);
    }
}

package edu.scheduling.loop;

import edu.scheduling.SchedulingHandler;
import java.time.LocalDateTime;


public interface LoopWorkerBatchFactory {

    Runnable create(Integer origin, SchedulingHandler handler, LocalDateTime beginTime, LocalDateTime endTime);
}

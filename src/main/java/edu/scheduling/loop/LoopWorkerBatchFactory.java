package edu.scheduling.loop;

import edu.scheduling.SchedulingHandler;
import java.time.LocalDateTime;


public interface LoopWorkerBatchFactory {

    LoopWorkerBatch create(Integer origin, SchedulingHandler handler, LocalDateTime beginTime, LocalDateTime endTime);
}

package edu.scheduling.loop;

import edu.model.TimeRange;
import edu.model.intensity.SchedulingIntensities;


public interface WorkerBatch {

    void start(Integer origin, TimeRange timeRange);

    void scheduleNext(SchedulingIntensities intensities, TimeRange range);
}

package edu.scheduling.loop;

import edu.model.intensity.SchedulingIntensities;
import java.time.LocalDateTime;


public interface Worker extends Runnable {

    void scheduleNext(SchedulingIntensities intensities, LocalDateTime time);
}

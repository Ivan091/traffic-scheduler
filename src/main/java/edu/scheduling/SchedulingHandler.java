package edu.scheduling;

import edu.model.intensity.SchedulingIntensities;
import java.time.LocalDateTime;


public interface SchedulingHandler {

    void handle(SchedulingIntensities schedulingIntensities, LocalDateTime time);
}

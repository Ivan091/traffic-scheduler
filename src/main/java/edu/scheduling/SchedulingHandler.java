package edu.scheduling;

import edu.model.intensity.SchedulingIntensities;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;


public interface SchedulingHandler extends BiConsumer<SchedulingIntensities, LocalDateTime> {

}

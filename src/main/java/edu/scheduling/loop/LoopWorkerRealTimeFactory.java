package edu.scheduling.loop;

import edu.scheduling.SchedulingHandler;


public interface LoopWorkerRealTimeFactory {

    Worker create(Integer origin, SchedulingHandler handler);
}

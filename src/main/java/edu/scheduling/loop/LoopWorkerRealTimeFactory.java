package edu.scheduling.loop;

import edu.scheduling.SchedulingHandler;


public interface LoopWorkerRealTimeFactory {

    LoopWorkerRealTime create(Integer origin, SchedulingHandler handler);
}

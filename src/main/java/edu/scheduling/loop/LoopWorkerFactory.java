package edu.scheduling.loop;

import edu.scheduling.SchedulingHandler;


public interface LoopWorkerFactory {

    Worker create(Integer origin, SchedulingHandler handler);
}

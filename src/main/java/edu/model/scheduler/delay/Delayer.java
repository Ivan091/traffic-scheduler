package edu.model.scheduler.delay;

public interface Delayer {

    Delay calculateDelay(Integer hour);
}

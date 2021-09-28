package edu.model.scheduler.delay;

public record DelayOrder(Long milliseconds) implements Delay {

    @Override
    public Long inMilliseconds() {
        return milliseconds;
    }
}

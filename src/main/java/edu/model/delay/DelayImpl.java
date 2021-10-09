package edu.model.delay;

public record DelayImpl(Long milliseconds) implements Delay {

    @Override
    public Long inMilliseconds() {
        return milliseconds;
    }
}

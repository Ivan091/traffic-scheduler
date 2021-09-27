package edu.model.scheduler.delay;

public class DelayDelta implements Delta {

    private final Double delta;

    public DelayDelta(Double delta) {
        this.delta = delta;
    }

    @Override
    public Double apply(Double x) {
        var left = x * (1 - delta);
        var right = x * (1 + delta);
        return left + Math.random() * (right - left);
    }
}

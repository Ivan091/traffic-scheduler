package edu.model.scheduler.delay;

import java.util.Map;
import java.util.function.Function;


public record HourDelayer(Map<Integer, Double> delays, Function<Long, Delay> delayFunction, Blur blur) implements Delayer {

    @Override
    public Delay calculateDelay(Integer hour) {
        var delay = delays.get(hour) * 1000;
        var milliseconds = Math.round(blur.blur(delay));
        return delayFunction.apply(milliseconds);
    }
}

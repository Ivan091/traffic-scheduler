package edu.model.scheduler.delay;

import java.util.Map;


public class HourDelayer implements Delayer {

    private final Map<Integer, Double> delays;

    private final Delta delta;

    public HourDelayer(Map<Integer, Double> delays,
                       Delta delta) {
        this.delays = delays;
        this.delta = delta;
    }

    @Override
    public Long calculateDelay(Integer hour) {
        var delay = delays.get(hour) * 1000;
        return Math.round(delta.apply(delay));
    }
}

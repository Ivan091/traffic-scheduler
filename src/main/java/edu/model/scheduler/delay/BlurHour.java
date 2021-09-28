package edu.model.scheduler.delay;

import org.apache.commons.lang3.RandomUtils;


public record BlurHour(Double blur) implements Blur {

    @Override
    public Double blur(Double x) {
        var left = x * (1 - blur);
        var right = x * (1 + blur);
        return RandomUtils.nextDouble(left, right);
    }
}

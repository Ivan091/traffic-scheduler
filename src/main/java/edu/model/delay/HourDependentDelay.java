package edu.model.delay;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Supplier;


public record HourDependentDelay(Map<Integer, Double> delays, Supplier<LocalDateTime> localDateTimeSupplier,
                                 Blur blur) implements Delay {

    @Override
    public Long inMilliseconds() {
        var hour = localDateTimeSupplier.get().getHour();
        var delayInMilliseconds = delays.get(hour) * 1000;
        return Math.round(blur.blur(delayInMilliseconds));
    }
}

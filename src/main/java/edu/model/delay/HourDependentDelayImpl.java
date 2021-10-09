package edu.model.delay;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Supplier;


public record HourDependentDelayImpl(Map<Integer, Double> delays, Supplier<LocalDateTime> localDateTimeSupplier,
                                     Blur blur) implements HourDependentDelay {

    @Override
    public Long inMilliseconds() {
        var hour = localDateTimeSupplier.get().getHour();
        var delayInMilliseconds = delays.get(hour) * 1000;
        return Math.round(blur.blur(delayInMilliseconds));
    }
}

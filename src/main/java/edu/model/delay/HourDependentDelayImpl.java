package edu.model.delay;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;


public record HourDependentDelayImpl(Map<Integer, Double> delays, Supplier<LocalDateTime> localDateTimeSupplier, Function<Long, Delay> delayFunction,
                                     Blur blur) implements HourDependentDelay {

    @Override
    public Long inMilliseconds() {
        var hour = localDateTimeSupplier.get().getHour();
        var delay = delays.get(hour) * 1000;
        var milliseconds = Math.round(blur.blur(delay));
        return delayFunction.apply(milliseconds).inMilliseconds();
    }
}

package edu.model.delay;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.function.Supplier;


public record DayDependentDelay(Delay workingDayDelayer, Delay weekendDelayer,
                                Supplier<LocalDateTime> localDateSupplier) implements Delay {

    @Override
    public Long inMilliseconds() {
        var dateTime = localDateSupplier.get();
        var day = dateTime.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return weekendDelayer.inMilliseconds();
        } else {
            return workingDayDelayer.inMilliseconds();
        }
    }
}

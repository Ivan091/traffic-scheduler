package edu.model.scheduler.delay;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.function.Supplier;


public record DayTypeDoorman(Delayer weekendDelayer, Delayer workingDayDelayer, Supplier<LocalDateTime> localDateSupplier) implements Doorman {

    @Override
    public Delay choose() {
        var dateTime = localDateSupplier.get();
        var day = dateTime.getDayOfWeek();
        var hour = dateTime.getHour();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return weekendDelayer.calculateDelay(hour);
        } else {
            return workingDayDelayer.calculateDelay(hour);
        }
    }
}

package edu.model.scheduler.delay;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.function.Supplier;


@Component
public class DayTypeDoorman implements Doorman {

    private final Delayer weekendDelayer;

    private final Delayer workingDayDelayer;

    private final Supplier<LocalDateTime> localDateSupplier;

    public DayTypeDoorman(@Qualifier("weekendDelayer") Delayer weekendDelayer, @Qualifier("workingDayDelayer") Delayer workingDayDelayer,
                          Supplier<LocalDateTime> localDateSupplier) {
        this.weekendDelayer = weekendDelayer;
        this.workingDayDelayer = workingDayDelayer;
        this.localDateSupplier = localDateSupplier;
    }

    @Override
    public Long choose() {
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

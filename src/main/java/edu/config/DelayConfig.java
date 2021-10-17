package edu.config;

import edu.model.delay.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
import java.util.function.Supplier;


@Configuration
public class DelayConfig {

    @Bean
    public Delay workingDayDelay(DelayProperties delayProperties, Supplier<LocalDateTime> localDateTimeSupplier, Blur blur) {
        return new HourDependentDelay(delayProperties.workingDay, localDateTimeSupplier, blur);
    }

    @Bean
    public Delay weekendDelay(DelayProperties delayProperties, Supplier<LocalDateTime> localDateTimeSupplier, Blur blur) {
        return new HourDependentDelay(delayProperties.weekend, localDateTimeSupplier, blur);
    }

    @Bean
    public Delay dayDependentDelay(Delay workingDayDelay, Delay weekendDelay, Supplier<LocalDateTime> localDateTimeSupplier) {
        return new DayDependentDelay(workingDayDelay, weekendDelay, localDateTimeSupplier);
    }

    @Bean
    public Blur blur(DelayProperties delayProperties) {
        return new BlurLinear(delayProperties.blur);
    }
}

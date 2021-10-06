package edu.config;

import edu.config.infrastructure.YamlSource;
import edu.config.validation.ValidDayConfig;
import edu.model.delay.*;
import edu.repository.entity.embeddable.Path;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;


@Configuration
public class DelayConfig {

    @Bean
    public Function<Long, Delay> delayFunction() {
        return DelayOrder::new;
    }

    @Bean
    public Delayer workingDayDelayer(DelayProperties delayProperties, Function<Long, Delay> delayFunction, Blur blur) {
        return new HourDelayer(delayProperties.weekend, delayFunction, blur);
    }

    @Bean
    public Delayer weekendDelayer(DelayProperties delayProperties, Function<Long, Delay> delayFunction, Blur blur) {
        return new HourDelayer(delayProperties.workingDay, delayFunction, blur);
    }

    @Bean
    public Blur delta(DelayProperties delayProperties) {
        return new BlurHour(delayProperties.blur);
    }

    @Bean
    public Doorman doorman(Delayer weekendDelayer, Delayer workingDayDelayer, Supplier<LocalDateTime> localDateSupplier) {
        return new DayTypeDoorman(weekendDelayer, workingDayDelayer, localDateSupplier);
    }

    @Bean
    public Supplier<Integer> pathDistribution(DelayProperties delayProperties) {
        var p = new PoissonDistribution(delayProperties.stationCount * 0.5);
        return p::sample;
    }

    @Component
    @Validated
    @YamlSource("order.yml")
    @ConfigurationProperties(prefix = "order")
    public static class DelayProperties {

        @ValidDayConfig
        private Map<Integer, Double> weekend;

        @ValidDayConfig
        private Map<Integer, Double> workingDay;

        @Min(0)
        private Integer stationCount;

        @Min(0)
        @Max(1)
        private Double blur;

        public void setStationCount(int stationCount) {
            this.stationCount = stationCount;
        }

        public void setBlur(Double blur) {
            this.blur = blur;
        }

        public void setWorkingDay(Map<Integer, Double> workingDay) {
            this.workingDay = workingDay;
        }

        public void setWeekend(Map<Integer, Double> weekend) {
            this.weekend = weekend;
        }
    }
}

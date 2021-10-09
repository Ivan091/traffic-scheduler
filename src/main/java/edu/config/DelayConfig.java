package edu.config;

import edu.config.infrastructure.YamlSource;
import edu.config.validation.ValidDayConfig;
import edu.model.delay.*;
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
import java.util.function.Supplier;


@Configuration
public class DelayConfig {

    @Bean
    public HourDependentDelay workingDayDelay(DelayProperties delayProperties, Supplier<LocalDateTime> localDateTimeSupplier, Blur blur) {
        return new HourDependentDelayImpl(delayProperties.workingDay, localDateTimeSupplier, blur);
    }

    @Bean
    public HourDependentDelay weekendDelay(DelayProperties delayProperties, Supplier<LocalDateTime> localDateTimeSupplier, Blur blur) {
        return new HourDependentDelayImpl(delayProperties.weekend, localDateTimeSupplier, blur);
    }

    @Bean
    public DayDependentDelay dayDependentDelay(HourDependentDelay workingDayDelay, HourDependentDelay weekendDelay, Supplier<LocalDateTime> localDateTimeSupplier) {
        return new DayDependentDelayImpl(workingDayDelay, weekendDelay, localDateTimeSupplier);
    }

    @Bean
    public Blur delta(DelayProperties delayProperties) {
        return new BlurLinear(delayProperties.blur);
    }

    @Bean
    public Supplier<Integer> pathDistribution(DelayProperties delayProperties) {
        return new PoissonDistribution(delayProperties.stationCount * 0.5)::sample;
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

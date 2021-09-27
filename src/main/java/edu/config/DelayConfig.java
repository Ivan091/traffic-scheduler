package edu.config;

import edu.model.scheduler.delay.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import java.util.Map;


@Configuration
public class DelayConfig {

    @Bean
    Delayer workingDayDelayer(DelayProperties delayProperties, Delta delta) {
        return new HourDelayer(delayProperties.weekend, delta);
    }

    @Bean
    Delayer weekendDelayer(DelayProperties delayProperties, Delta delta) {
        return new HourDelayer(delayProperties.workingDay, delta);
    }

    @Bean
    Delta delta(DelayProperties delayProperties) {
        return new DelayDelta(delayProperties.delta);
    }

    @Component
    @YamlSource("order.yml")
    @ConfigurationProperties(prefix = "order")
    public static class DelayProperties {

        private Map<Integer, Double> weekend;

        private Map<Integer, Double> workingDay;

        private Double delta;

        public void setDelta(Double delta) {
            this.delta = delta;
        }

        public void setWorkingDay(Map<Integer, Double> workingDay) {
            this.workingDay = workingDay;
        }

        public void setWeekend(Map<Integer, Double> weekend) {
            this.weekend = weekend;
        }
    }
}

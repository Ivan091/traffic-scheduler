package edu.config;

import edu.config.infrastructure.YamlSource;
import edu.config.validation.ValidDayConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Min;
import java.util.Map;


@Component
@Validated
@YamlSource("order.yml")
@ConfigurationProperties(prefix = "order")
public class DelayProperties {

    @ValidDayConfig
    public Map<Integer, Double> weekend;

    @ValidDayConfig
    public Map<Integer, Double> workingDay;

    @Min(1)
    public Integer maxSeatsCount;

    @Min(2)
    public Integer stationCount;

    public void setMaxSeatsCount(Integer maxSeatsCount) {
        this.maxSeatsCount = maxSeatsCount;
    }

    public void setStationCount(int stationCount) {
        this.stationCount = stationCount;
    }

    public void setWorkingDay(Map<Integer, Double> workingDay) {
        this.workingDay = workingDay;
    }

    public void setWeekend(Map<Integer, Double> weekend) {
        this.weekend = weekend;
    }
}

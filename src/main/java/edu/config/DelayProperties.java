package edu.config;

import edu.config.infrastructure.YamlSource;
import edu.config.validation.ValidDayConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Max;
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

    @Min(2)
    public Integer stationCount;

    @Min(0)
    @Max(1)
    public Double blur;

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

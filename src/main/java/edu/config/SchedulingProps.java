package edu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;


@Data
@Configuration
@ConfigurationProperties(prefix = "scheduling")
public class SchedulingProps {

    public Mode mode;

    public LocalDate date;

    public enum Mode {
        REAL_TIME,
        ONE_DAY,
    }
}

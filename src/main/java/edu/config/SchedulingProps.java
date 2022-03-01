package edu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;


@Data
@Configuration
@ConfigurationProperties(prefix = "scheduling")
@Validated
public class SchedulingProps {

    @Min(0)
    @Max(1)
    public Double intensityDispersion;

    public LocalDate beginDate;

    public LocalDate endDate;
}

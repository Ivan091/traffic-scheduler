package edu.model.scheduler;

import edu.config.YamlPropertySourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import java.util.function.Function;


@Service
@PropertySource(value = "classpath:order.yml", factory = YamlPropertySourceFactory.class)
public class Delta implements Function<Long, Long> {

    private final Double delta;

    public Delta(@Value("${order.delta}") Double delta) {
        this.delta = delta;
    }

    @Override
    public Long apply(Long x) {
        var left = x * (1 - delta);
        var right = x * (1 + delta);
        return Math.round(left + Math.random() * (right - left));
    }
}

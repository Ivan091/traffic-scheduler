package edu.config;

import edu.model.Path;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Supplier;
import java.util.stream.Stream;


@Configuration
public class StationConfig {

    @Bean
    public Supplier<Integer> destinationSupplier(DelayProperties delayProperties) {
        var nd = new NormalDistribution((delayProperties.stationsCount - 1) * 0.5, delayProperties.stationsCount * 0.2);
        return () -> Stream.generate(nd::sample)
                .map(d -> (int) Math.round(d))
                .takeWhile(i -> i >= 1 && i < delayProperties.stationsCount)
                .limit(1)
                .findFirst()
                .orElseThrow();
    }

    @Bean
    public Supplier<Path> pathSupplier(Supplier<Integer> destinationSupplier) {
        return () -> Stream.generate(() -> {
                    var destination = destinationSupplier.get();
                    var origin = RandomUtils.nextInt(0, destination);
                    return new Path(origin, destination);
                })
                .takeWhile(Path::isRightWay)
                .limit(1)
                .findFirst()
                .orElseThrow();
    }
}

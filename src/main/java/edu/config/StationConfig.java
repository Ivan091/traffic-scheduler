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
    public Supplier<Integer> originSupplier(DelayProperties delayProperties) {
        var nd = new NormalDistribution((delayProperties.stationCount - 1) * 0.5, delayProperties.stationCount * 0.2);
        return () -> Stream.generate(nd::sample)
                .map(d -> (int) Math.round(d))
                .dropWhile(i -> i < 0 || i >= delayProperties.stationCount)
                .limit(1)
                .findFirst()
                .orElseThrow();
    }

    @Bean
    public Supplier<Integer> destinationSupplier(DelayProperties delayProperties) {
        return () -> RandomUtils.nextInt(0, delayProperties.stationCount);
    }

    @Bean
    public Supplier<Path> pathSupplier(Supplier<Integer> originSupplier, Supplier<Integer> destinationSupplier) {
        return () -> Stream.generate(() -> new Path(originSupplier.get(), destinationSupplier.get()))
                .dropWhile(p -> p.origin.equals(p.destination))
                .limit(1)
                .findFirst()
                .orElseThrow();
    }
}

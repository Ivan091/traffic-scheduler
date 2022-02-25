package edu.scheduling;

import edu.config.SchedulingProps;
import edu.model.intensity.PathIntensity;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.function.Function;


@Component
public final class IntensityDisperser implements Function<PathIntensity, PathIntensity> {

    @Autowired
    private SchedulingProps props;

    @Override
    public PathIntensity apply(PathIntensity pathIntensity) {
        var dispersion = RandomUtils.nextDouble(0, props.intensityDispersion * 2) - props.intensityDispersion * 0.5;
        var newIntensity = pathIntensity.getIntensity() * Math.max(1 + dispersion, 0.001);
        return pathIntensity.withIntensity(newIntensity);
    }
}

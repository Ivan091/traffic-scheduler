package edu.service;

import edu.model.intensity.PathIntensity;
import edu.model.intensity.SchedulingIntensities;
import edu.scheduling.RandomWheelSelector;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class SchedulingIntensitiesService {

    public RandomWheelSelector<Integer> generateSelector(List<PathIntensity> intensities) {
        return new RandomWheelSelector<>(
                intensities.stream().map(x -> new ImmutablePair<>(x.getIntensity(), x.getPath().getDestination())).toList()
        );
    }

    public Double sumOfIntensities(List<PathIntensity> intensities) {
        return intensities.stream().mapToDouble(PathIntensity::getIntensity).sum();
    }

    public SchedulingIntensities toSingleOrigin(List<PathIntensity> intensities, Integer origin) {
        var destinationSelector = generateSelector(intensities);
        var probabilitySum = sumOfIntensities(intensities);
        return new SchedulingIntensities(origin, probabilitySum, destinationSelector);
    }
}

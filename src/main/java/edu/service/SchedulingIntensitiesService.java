package edu.service;

import edu.model.intensity.PathIntensity;
import edu.model.intensity.SchedulingIntensities;
import edu.scheduling.RandomWheelSelector;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


@Service
public final class SchedulingIntensitiesService {

    public List<ImmutablePair<Double, Integer>> probabilitiesAsPairList(List<PathIntensity> intensities) {
        return intensities.stream().map(x -> new ImmutablePair<>(x.getIntensity(), x.getPath().getDestination())).collect(Collectors.toList());
    }

    public Double sumOfIntensities(List<PathIntensity> intensities) {
        return intensities.stream().mapToDouble(PathIntensity::getIntensity).sum();
    }

    public List<SchedulingIntensities> toSingleOrigin(List<PathIntensity> intensities) {
        return intensities.stream()
                .collect(groupingBy(x -> x.getPath().getOrigin()))
                .entrySet()
                .stream()
                .map(e -> {
                    var origin = e.getKey();
                    var singleOriginIntensities = e.getValue();
                    var randomWheelSelector = new RandomWheelSelector<>(probabilitiesAsPairList(singleOriginIntensities));
                    return new SchedulingIntensities(origin, randomWheelSelector);
                }).collect(toList());
    }
}

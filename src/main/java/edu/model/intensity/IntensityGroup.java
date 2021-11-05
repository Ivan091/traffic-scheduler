package edu.model.intensity;

import edu.model.scheduling.RandomWheelSelector;
import lombok.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


@EqualsAndHashCode
@ToString
@AllArgsConstructor
public final class IntensityGroup {

    private final List<Intensity> intensities;

    public List<ImmutablePair<Double, Integer>> probabilitiesAsPairList() {
        return intensities.stream().map(x -> new ImmutablePair<>(x.getIntensity(), x.getDestination())).collect(Collectors.toList());
    }

    public Double sumOfIntensities() {
        return intensities.stream().map(Intensity::getIntensity).mapToDouble(Double::doubleValue).sum();
    }

    public List<SingleOriginIntensities> toSingleOrigin() {
        return intensities.stream()
                .collect(groupingBy(Intensity::getOrigin))
                .entrySet()
                .stream()
                .map(e -> {
                    var intensityGroup = new IntensityGroup(e.getValue());
                    var randomWheelSelector = new RandomWheelSelector<>(intensityGroup.probabilitiesAsPairList());
                    return new SingleOriginIntensities(e.getKey(), randomWheelSelector, intensityGroup);
                }).collect(toList());
    }
}

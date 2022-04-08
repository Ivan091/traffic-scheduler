package edu.scheduling;

import lombok.*;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


@EqualsAndHashCode
@ToString
public class RandomWheelSelector<V> implements Supplier<V> {

    @Getter
    private final double maxProbability;

    private final List<ImmutablePair<Double, V>> resultPairs;

    public RandomWheelSelector(List<ImmutablePair<Double, V>> map) {
        var sum = 0d;
        resultPairs = new ArrayList<>(map.size());
        for (var e : map) {
            sum += e.getKey();
            resultPairs.add(new ImmutablePair<>(sum, e.getRight()));
        }
        maxProbability = sum;
    }

    @Override
    public V get() {
        var rnd = RandomUtils.nextDouble(0.0000001, maxProbability);
        for (ImmutablePair<Double, V> pair : resultPairs) {
            if (pair.getKey() > rnd) {
                return pair.getValue();
            }
        }
        throw new IllegalArgumentException();
    }
}

package edu.util;

import org.apache.commons.lang3.RandomUtils;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;


public class RouletteWheelSelectior<E> implements Supplier<E> {

    private final List<E> data;

    private final List<Double> limits;

    private final BiFunction<Double, Double, Double> limitedRandomGenerator;

    public RouletteWheelSelectior(Map<E, Double> items, BiFunction<Double, Double, Double> limitedRandomGenerator) {
        this.limitedRandomGenerator = limitedRandomGenerator;
        data = new ArrayList<>(items.size());
        limits = new ArrayList<>(items.size() + 1);
        var limit = 0d;
        limits.add(limit);
        for (var pair : items.entrySet()) {
            data.add(pair.getKey());
            limit += pair.getValue();
            limits.add(limit);
        }
    }

    public RouletteWheelSelectior(Map<E, Double> items) {
        this(items, RandomUtils::nextDouble);
    }

    @Override
    public E get() {
        var rValue = limitedRandomGenerator.apply(limits.get(0), limits.get(limits.size() - 1));
        for (var i = 0; i < data.size() - 1; i++) {
            if (rValue >= limits.get(i)) {
                return data.get(i);
            }
        }
        throw new IllegalArgumentException();
    }
}

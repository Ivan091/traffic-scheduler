package edu.model.intensity;

import java.util.function.Supplier;


public record SchedulingIntensities(Integer origin,
                                    Supplier<Integer> destinationSupplier) {

}

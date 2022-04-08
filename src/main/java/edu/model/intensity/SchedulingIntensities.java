package edu.model.intensity;

import lombok.Value;
import lombok.With;
import java.util.function.Supplier;


@Value
@With
public class SchedulingIntensities {

    Integer origin;

    Double probabilitySum;

    Supplier<Integer> destinationSupplier;
}

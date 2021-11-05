package edu.model.intensity;

import edu.model.order.Path;
import lombok.*;
import java.util.function.Supplier;


@EqualsAndHashCode
@ToString
@AllArgsConstructor
public final class SingleOriginIntensities {

    private final Integer origin;

    private final Supplier<Integer> destinationSupplier;

    private final IntensityGroup intensityGroup;

    public Path generatePath() {
        return new Path(origin, destinationSupplier.get());
    }

    public Double sumOfIntensities() {
        return intensityGroup.sumOfIntensities();
    }
}

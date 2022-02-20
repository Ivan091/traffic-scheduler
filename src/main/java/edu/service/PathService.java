package edu.service;

import edu.model.intensity.SchedulingIntensities;
import edu.model.order.Path;
import org.springframework.stereotype.Service;


@Service
public final class PathService {

    public Path generatePath(SchedulingIntensities x) {
        return new Path(x.origin(), x.destinationSupplier().get());
    }
}

package edu.model.station;

import edu.repository.entity.embeddable.Path;
import org.springframework.stereotype.Component;
import java.util.function.Supplier;

@Component
public class PathSupplier implements Supplier<Path> {

    private final Supplier<Integer> pathDistribution;

    public PathSupplier(Supplier<Integer> pathDistribution) {
        this.pathDistribution = pathDistribution;
    }

    @Override
    public Path get() {
        Path p;
        do {
            p = new Path(pathDistribution.get(), pathDistribution.get());
        } while (p.origin.equals(p.destination));
        return p;
    }
}

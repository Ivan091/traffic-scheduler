package edu.model.scheduler.statiom;

import org.springframework.stereotype.Component;
import java.util.function.Supplier;


@Component
public class SourceSupplier implements Supplier<Integer> {

    @Override
    public Integer get() {
        return null;
    }
}

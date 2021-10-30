package edu.model.scheduling;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@EqualsAndHashCode
@ToString
@Service
public final class NextMomentRule {

    public LocalDateTime calculateNext(LocalDateTime current, Double probabilitySum) {
        var delta = -Math.log(RandomUtils.nextDouble(0.03, 0.97)) / probabilitySum;
        var roundedDelta = Math.round(delta);
        return current.plus(roundedDelta, ChronoUnit.SECONDS);
    }
}

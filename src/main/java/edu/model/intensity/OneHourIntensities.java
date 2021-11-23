package edu.model.intensity;

import edu.model.scheduling.NextMomentRule;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;


@EqualsAndHashCode
@ToString
@AllArgsConstructor
public final class OneHourIntensities {

    private final List<SingleOriginIntensities> singleOriginIntensities;

    private final NextMomentRule nextMomentRule;

    private final BiConsumer<SingleOriginIntensities, LocalDateTime> handler;

    public static OneHourIntensities of(List<Intensity> singleOriginIntensities,
                                        NextMomentRule nextMomentRule,
                                        BiConsumer<SingleOriginIntensities, LocalDateTime> handler) {
        return new OneHourIntensities(new IntensityGroup(singleOriginIntensities).toSingleOrigin(), nextMomentRule, handler);
    }

    public void planForTheNextHour(LocalDateTime startDateTime) {
        singleOriginIntensities.parallelStream().forEach(x -> {
                    var probabilitySum = x.sumOfIntensities();
                    var startHour = startDateTime.getHour();
                    var planTime = nextMomentRule.calculateNext(startDateTime, probabilitySum);
                    while (planTime.getHour() == startHour) {
                        handler.accept(x, planTime);
                        planTime = nextMomentRule.calculateNext(planTime, probabilitySum);
                    }
                }
        );
    }
}

package edu.model.intensity;

import edu.model.order.Order;
import edu.model.scheduling.NextMomentRule;
import edu.repo.OrderRepo;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;


@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Slf4j
public final class CurrentHourIntensities {

    private final List<SingleOriginIntensities> singleOriginIntensities;

    private final NextMomentRule nextMomentRule;

    private final Supplier<LocalDateTime> localDateTimeSupplier;

    private final TaskScheduler taskScheduler;

    private final OrderRepo orderRepo;

    public static CurrentHourIntensities of(List<Intensity> singleOriginIntensities, NextMomentRule nextMomentRule,
                                            Supplier<LocalDateTime> localDateTimeSupplier, TaskScheduler taskScheduler, OrderRepo orderRepo) {
        return new CurrentHourIntensities(new IntensityGroup(singleOriginIntensities).toSingleOrigin(), nextMomentRule, localDateTimeSupplier, taskScheduler, orderRepo);
    }

    public void planForTheNextHour() {
        singleOriginIntensities.parallelStream().forEach(x -> {
                    var probabilitySum = x.sumOfProbabilities();
                    var planTime = localDateTimeSupplier.get();
                    var startHour = planTime.getHour();
                    planTime = nextMomentRule.calculateNext(planTime, probabilitySum);
                    while (planTime.getHour() == startHour) {
                        log.info("Planned to {} ", planTime);
                        taskScheduler.schedule(() -> executeSingleOrigin(x), Timestamp.valueOf(planTime));
                        planTime = nextMomentRule.calculateNext(planTime, probabilitySum);
                    }
                }
        );
    }

    private void executeSingleOrigin(SingleOriginIntensities singleOriginIntensities) {
        var order = Order.of(singleOriginIntensities.generatePath(), 1, localDateTimeSupplier.get());
        orderRepo.save(order);
    }
}

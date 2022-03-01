package edu.scheduling;

import edu.model.intensity.PathIntensity;
import edu.model.intensity.SchedulingIntensities;
import edu.service.SchedulingIntensitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;


@Service
public final class NextHourScheduler {

    @Autowired
    private NextMomentRule nextMomentRule;

    @Autowired
    private SchedulingIntensitiesService schedulingIntensitiesService;

    public void scheduleForTheNextHour(List<PathIntensity> intensities, LocalDateTime startDateTime, BiConsumer<SchedulingIntensities, LocalDateTime> handler) {
        schedulingIntensitiesService.toSingleOrigin(intensities).parallelStream().forEach(x -> {
                    var probabilitySum = schedulingIntensitiesService.sumOfIntensities(intensities);
                    var startHour = startDateTime.getHour();
                    var planTime = nextMomentRule.calculateNext(startDateTime, probabilitySum).withNano(0);
                    while (planTime.getHour() == startHour) {
                        handler.accept(x, planTime);
                        planTime = nextMomentRule.calculateNext(planTime, probabilitySum);
                    }
                }
        );
    }
}

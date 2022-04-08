package edu.scheduling.loop;

import edu.scheduling.NextMomentRule;
import edu.scheduling.SchedulingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.function.Supplier;


@Service
@Slf4j
public class LoopWorkerService {

    @Autowired
    private NextMomentRule nextMomentRule;

    @Autowired
    private LoopWorkerCache loopWorkerCache;

    @Autowired
    private Supplier<LocalDateTime> localDateTimeSupplier;

    public LocalDateTime planTime(Integer origin, SchedulingHandler handler) {
        var currentTime = localDateTimeSupplier.get();
        return planTime(origin, handler, currentTime);
    }

    public LocalDateTime planTime(Integer origin, SchedulingHandler handler, LocalDateTime currentTime) {
        var scheduling = loopWorkerCache.requestScheduling(currentTime, origin);
        var planTime = nextMomentRule.calculateNext(currentTime, scheduling.getProbabilitySum()).withNano(0);
        handler.handle(scheduling, planTime);
        return planTime;
    }
}

package edu.scheduling.loop;

import edu.model.intensity.PathIntensity;
import edu.repo.IntensityRepo;
import edu.scheduling.*;
import edu.service.SchedulingIntensitiesService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;


@Service
@Slf4j
public class LoopWorkerService {

    private final static String CACHE_NAME = "intensities";

    @Autowired
    CacheManager cacheManager;

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private IntensityDisperser intensityDisperser;

    @Autowired
    private Supplier<LocalDateTime> localDateTimeSupplier;

    @Autowired
    private NextMomentRule nextMomentRule;

    @Autowired
    private SchedulingIntensitiesService schedulingIntensitiesService;

    @Scheduled(cron = "0 0 * * * *")
    @SneakyThrows
    public void evictAllCaches() {
        log.info("Refreshing intensities cache");
        Objects.requireNonNull(cacheManager.getCache(CACHE_NAME)).clear();
    }

    @Cacheable(value = CACHE_NAME, key = "{#time.hour, #origin}")
    public List<PathIntensity> requestSingleOriginIntensities(LocalDateTime time, Integer origin) {
        return intensityRepo
                .findByObservationIntervalAndPathOrigin(time.getHour(), origin)
                .stream()
                .filter(x -> x.getIntensity() > 0)
                .map(intensityDisperser)
                .toList();
    }

    public LocalDateTime schedule(Integer origin, SchedulingHandler handler, LocalDateTime currentTime) {
        var intensities = requestSingleOriginIntensities(currentTime, origin);
        if (intensities.isEmpty()) {
            log.error("No intensities for origin={} were found", origin);
            return LocalDateTime.MIN;
        }
        var scheduling = schedulingIntensitiesService.toSingleOrigin(intensities, origin);
        var planTime = nextMomentRule.calculateNext(currentTime, scheduling.getProbabilitySum()).withNano(0);
        handler.accept(scheduling, planTime);
        return planTime;
    }

    public void schedule(Integer origin, SchedulingHandler handler, Consumer<LocalDateTime> callBack) {
        var currentTime = localDateTimeSupplier.get();
        var intensities = requestSingleOriginIntensities(currentTime, origin);
        if (intensities.isEmpty()) {
            log.error("No intensities for origin={} were found", origin);
            return;
        }
        var scheduling = schedulingIntensitiesService.toSingleOrigin(intensities, origin);
        var planTime = nextMomentRule.calculateNext(currentTime, scheduling.getProbabilitySum()).withNano(0);
        handler.accept(scheduling, planTime);
        callBack.accept(planTime);
    }
}

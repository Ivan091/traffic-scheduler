package edu.scheduling.loop;

import edu.model.intensity.SchedulingIntensities;
import edu.repo.IntensityRepo;
import edu.scheduling.IntensityDisperser;
import edu.service.SchedulingIntensitiesService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Objects;


@Service
@Slf4j
public class LoopWorkerCache {

    private final static String CACHE_NAME = "intensities";

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private IntensityDisperser intensityDisperser;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private SchedulingIntensitiesService schedulingIntensitiesService;

    @SneakyThrows
    @Scheduled(cron = "0 0 * * * *")
    public void evictCache() {
        log.info("Refreshing intensities cache");
        Objects.requireNonNull(cacheManager.getCache(CACHE_NAME)).clear();
    }

    @Cacheable(value = CACHE_NAME, key = "{#time.hour, #origin}")
    public SchedulingIntensities requestScheduling(LocalDateTime time, Integer origin) {
        var intensities = intensityRepo
                .findByObservationIntervalAndPathOrigin(time.getHour(), origin)
                .stream()
                .filter(x -> x.getIntensity() > 0)
                .map(intensityDisperser)
                .toList();
        if (intensities.isEmpty()) {
            throw new IllegalStateException(String.format("No intensities for origin=%d were found", origin));
        }
        return schedulingIntensitiesService.toSingleOrigin(intensities, origin);
    }
}

package edu.scheduling.scheduler;

import edu.repo.IntensityRepo;
import edu.scheduling.handler.DBHandler;
import edu.scheduling.loop.LoopWorkerRealTimeFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import java.util.Objects;


@Service
@Slf4j
public class RealTimeScheduler {

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private DBHandler dbHandler;

    @Autowired
    private LoopWorkerRealTimeFactory loopWorkerRealTimeFactory;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private TaskScheduler taskScheduler;

    @SneakyThrows
    public void evictAllCaches() {
        log.info("Refreshing intensities cache");
        cacheManager.getCacheNames().forEach(x -> Objects.requireNonNull(cacheManager.getCache(x)).clear());
    }

    public void run() {
        taskScheduler.schedule(this::evictAllCaches, new CronTrigger("0 0 * * * *"));
        intensityRepo.getOrigins()
                .stream()
                .map(o -> loopWorkerRealTimeFactory.create(o, dbHandler))
                .forEach(Runnable::run);
    }
}

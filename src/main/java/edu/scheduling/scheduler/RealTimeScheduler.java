package edu.scheduling.scheduler;

import edu.repo.IntensityRepo;
import edu.scheduling.IntensityDisperser;
import edu.scheduling.NextHourScheduler;
import edu.scheduling.handler.DBHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Service
public final class RealTimeScheduler {

    @Autowired
    private DBHandler realTimeHandler;

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private Supplier<LocalDateTime> localDateTimeSupplier;

    @Autowired
    private NextHourScheduler nextHourScheduler;

    @Autowired
    private IntensityDisperser intensityDisperser;

    public void run() {
        Runnable task = () -> {
            var currentTime = localDateTimeSupplier.get();
            var intensities = intensityRepo.findByObservationInterval(currentTime.getHour());
            intensities = intensities.stream().filter(x -> x.getIntensity() > 0).map(intensityDisperser).collect(Collectors.toList());
            nextHourScheduler.scheduleForTheNextHour(intensities, currentTime, realTimeHandler);
        };
        var trigger = new CronTrigger("0 0 * * * *");
        taskScheduler.schedule(task, trigger);
        task.run();
    }
}

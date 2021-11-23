package edu.model.scheduling;

import edu.model.intensity.Intensity;
import edu.model.intensity.OneHourIntensities;
import edu.repo.IntensityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Service
@ConditionalOnProperty(name = "scheduling.mode", havingValue = "real_time")
public final class RealTimeScheduler implements Runnable {

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private Supplier<LocalDateTime> localDateTimeSupplier;

    @Autowired
    private Function<List<Intensity>, OneHourIntensities> currentHourIntensitiesFactory;

    @Override
    @Scheduled(cron = "0 0 * * * *")
    public void run() {
        var currentTime = localDateTimeSupplier.get();
        var intensities = intensityRepo.findByObservationInterval(currentTime.getHour());
        intensities = intensities.stream().filter(x -> x.getIntensity() > 0).collect(Collectors.toList());
        currentHourIntensitiesFactory.apply(intensities).planForTheNextHour(localDateTimeSupplier.get());
    }
}

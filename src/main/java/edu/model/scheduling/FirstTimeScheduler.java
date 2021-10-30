package edu.model.scheduling;

import edu.model.intensity.CurrentHourIntensities;
import edu.model.intensity.Intensity;
import edu.repo.IntensityRepo;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@EqualsAndHashCode
@ToString
@Service
public final class FirstTimeScheduler implements Runnable {

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private Supplier<LocalDateTime> localDateTimeSupplier;

    @Autowired
    private Function<List<Intensity>, CurrentHourIntensities> currentHourIntensitiesFactory;

    @Override
    @Scheduled(cron = "0 0 * * * *")
    public void run() {
        var currentTime = localDateTimeSupplier.get();
        var intensities = intensityRepo.findByObservationInterval(currentTime.getHour());
        intensities = intensities.stream().filter(x -> x.getIntensity() > 0).collect(Collectors.toList());
        currentHourIntensitiesFactory.apply(intensities).planForTheNextHour();
    }
}

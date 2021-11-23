package edu.model.scheduling;

import edu.config.SchedulingProps;
import edu.model.intensity.Intensity;
import edu.model.intensity.OneHourIntensities;
import edu.repo.IntensityRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Slf4j
@ConditionalOnProperty(name = "scheduling.mode", havingValue = "one_day")
public final class OneDayScheduler implements Runnable {

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private Function<List<Intensity>, OneHourIntensities> currentHourIntensitiesFactory;

    @Autowired
    private SchedulingProps schedulingProps;

    @Override
    public void run() {
        for (int i = 0; i < 24; i++) {
            var planTime = schedulingProps.getDate().atTime(i, 0);
            var intensities = intensityRepo.findByObservationInterval(planTime.getHour());
            intensities = intensities.stream().filter(x -> x.getIntensity() > 0).collect(Collectors.toList());
            currentHourIntensitiesFactory.apply(intensities).planForTheNextHour(planTime);
        }
    }
}

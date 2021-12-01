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
import java.util.stream.StreamSupport;


@Service
@Slf4j
@ConditionalOnProperty(name = "scheduling.mode", havingValue = "one_day")
public final class MultipleDaysScheduler implements Runnable {

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private Function<List<Intensity>, OneHourIntensities> currentHourIntensitiesFactory;

    @Autowired
    private SchedulingProps props;

    @Override
    public void run() {
        var intensityGroup = StreamSupport.stream(intensityRepo.findAll().spliterator(), true)
                .filter(x -> x.getIntensity() > 0).collect(Collectors.groupingBy(Intensity::getObservationInterval));
        var endDate = props.getEndDate();
        for (var currentDate = props.getBeginDate(); currentDate.isBefore(endDate); currentDate = currentDate.plusDays(1)) {
            for (int i = 0; i < 24; i++) {
                var planTime = currentDate.atTime(i, 0);
                currentHourIntensitiesFactory.apply(intensityGroup.get(i)).planForTheNextHour(planTime);
            }
        }
    }
}

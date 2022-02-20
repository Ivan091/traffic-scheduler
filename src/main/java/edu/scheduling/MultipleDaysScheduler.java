package edu.scheduling;

import edu.config.SchedulingProps;
import edu.model.intensity.Intensity;
import edu.repo.IntensityRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@Slf4j
@ConditionalOnProperty(name = "scheduling.mode", havingValue = "one_day")
public final class MultipleDaysScheduler implements Runnable {

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private SchedulingProps props;

    @Autowired
    private NextHourScheduler nextHourScheduler;

    @Override
    public void run() {
        var intensityGroup = StreamSupport.stream(intensityRepo.findAll().spliterator(), true)
                .filter(x -> x.intensity() > 0).collect(Collectors.groupingBy(Intensity::observationInterval));
        var endDate = props.getEndDate();
        for (var currentDate = props.getBeginDate(); currentDate.isBefore(endDate); currentDate = currentDate.plusDays(1)) {
            for (int i = 0; i < 24; i++) {
                var planTime = currentDate.atTime(i, 0);
                nextHourScheduler.scheduleForTheNextHour(intensityGroup.get(i), planTime);
            }
        }
    }
}

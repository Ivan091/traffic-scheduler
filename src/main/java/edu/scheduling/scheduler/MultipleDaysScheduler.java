package edu.scheduling.scheduler;

import edu.config.SchedulingProps;
import edu.model.intensity.PathIntensity;
import edu.repo.IntensityRepo;
import edu.scheduling.IntensityDisperser;
import edu.scheduling.NextHourScheduler;
import edu.scheduling.handler.CSVFileHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@Slf4j
public final class MultipleDaysScheduler {

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private SchedulingProps props;

    @Autowired
    private CSVFileHandler fileHandler;

    @Autowired
    private NextHourScheduler nextHourScheduler;

    @Autowired
    private IntensityDisperser intensityDisperser;

    public void run() {
        var intensityGroup = StreamSupport.stream(intensityRepo.findAll().spliterator(), true)
                .filter(x -> x.getIntensity() > 0).map(intensityDisperser).collect(Collectors.groupingBy(PathIntensity::getObservationInterval));
        var endDate = props.getEndDate();
        for (var currentDate = props.getBeginDate(); currentDate.isBefore(endDate); currentDate = currentDate.plusDays(1)) {
            for (int i = 0; i < 24; i++) {
                var planTime = currentDate.atTime(i, 0);
                nextHourScheduler.scheduleForTheNextHour(intensityGroup.get(i), planTime, fileHandler);
            }
        }
    }
}

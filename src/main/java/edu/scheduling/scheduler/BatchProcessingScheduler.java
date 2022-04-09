package edu.scheduling.scheduler;

import edu.config.SchedulingProps;
import edu.model.TimeRange;
import edu.repo.IntensityRepo;
import edu.scheduling.loop.WorkerBatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class BatchProcessingScheduler {

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private WorkerBatch workerBatch;

    @Autowired
    private SchedulingProps props;

    public void run() {
        intensityRepo.getOrigins().forEach(o -> workerBatch.start(o, new TimeRange(props.beginDate, props.endDate)));
    }
}

package edu.scheduling.scheduler;

import edu.config.SchedulingProps;
import edu.repo.IntensityRepo;
import edu.scheduling.handler.CSVFileHandler;
import edu.scheduling.loop.LoopWorkerBatchFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class BatchProcessingScheduler {

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private SchedulingProps props;

    @Autowired
    private CSVFileHandler fileHandler;

    @Autowired
    private LoopWorkerBatchFactory loopWorkerBatchFactory;

    public void run() {
        intensityRepo.getOrigins()
                .stream()
                .map(o -> loopWorkerBatchFactory.create(o, fileHandler, props.getEndDate().atStartOfDay(), props.getBeginDate().atStartOfDay()))
                .forEach(Runnable::run);
    }
}

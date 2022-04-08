package edu.scheduling.scheduler;

import edu.repo.IntensityRepo;
import edu.scheduling.handler.CSVFileHandler;
import edu.scheduling.loop.LoopWorkerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class BatchProcessingScheduler {

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private CSVFileHandler fileHandler;

    @Autowired
    private LoopWorkerFactory loopWorkerBatchFactory;

    public void run() {
        intensityRepo.getOrigins()
                .parallelStream()
                .map(o -> loopWorkerBatchFactory.create(o, fileHandler))
                .forEach(Runnable::run);
    }
}

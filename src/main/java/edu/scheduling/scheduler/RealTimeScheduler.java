package edu.scheduling.scheduler;

import edu.repo.IntensityRepo;
import edu.scheduling.handler.DBHandler;
import edu.scheduling.loop.LoopWorkerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class RealTimeScheduler {

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private DBHandler dbHandler;

    @Autowired
    private LoopWorkerFactory loopWorkerRealTimeFactory;

    public void run() {
        intensityRepo.getOrigins()
                .parallelStream()
                .map(o -> loopWorkerRealTimeFactory.create(o, dbHandler))
                .forEach(Runnable::run);
    }
}

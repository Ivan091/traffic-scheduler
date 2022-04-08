package edu.scheduling.scheduler;

import edu.repo.IntensityRepo;
import edu.scheduling.handler.DBHandler;
import edu.scheduling.loop.LoopWorkerRealTimeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RealTimeScheduler {

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private DBHandler dbHandler;

    @Autowired
    private LoopWorkerRealTimeFactory loopWorkerRealTimeFactory;

    public void run() {
        intensityRepo.getOrigins()
                .stream()
                .map(o -> loopWorkerRealTimeFactory.create(o, dbHandler))
                .forEach(Runnable::run);
    }
}

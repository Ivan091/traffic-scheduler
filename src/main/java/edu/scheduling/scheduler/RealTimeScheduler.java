package edu.scheduling.scheduler;

import edu.repo.IntensityRepo;
import edu.scheduling.loop.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class RealTimeScheduler {

    @Autowired
    private IntensityRepo intensityRepo;

    @Autowired
    private Worker worker;

    public void run() {
        intensityRepo.getOrigins().forEach(x -> worker.start(x));
    }
}

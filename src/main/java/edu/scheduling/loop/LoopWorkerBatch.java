package edu.scheduling.loop;

import edu.scheduling.SchedulingHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;


@Slf4j
@AllArgsConstructor
public class LoopWorkerBatch implements Runnable {

    private final Integer origin;

    private final SchedulingHandler handler;

    private final LoopWorkerService loopWorkerService;

    private final LocalDateTime endTime;

    private LocalDateTime currentTime;

    @Override
    public void run() {
        while (currentTime.isBefore(endTime)) {
            currentTime = loopWorkerService.schedule(origin, handler, currentTime);
        }
    }
}

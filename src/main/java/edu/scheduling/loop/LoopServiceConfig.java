package edu.scheduling.loop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;


@Configuration
public class LoopServiceConfig {

    @Bean
    public LoopWorkerRealTimeFactory scheduleRealTime(TaskScheduler taskScheduler, LoopWorkerService loopWorkerService) {
        return (origin, handler) -> new LoopWorkerRealTime(origin, handler, taskScheduler, loopWorkerService);
    }

    @Bean
    public LoopWorkerBatchFactory scheduleBatch(LoopWorkerService loopWorkerService) {
        return (origin, handler, endTime, currentTime) -> new LoopWorkerBatch(origin, handler, loopWorkerService, endTime, currentTime);
    }
}

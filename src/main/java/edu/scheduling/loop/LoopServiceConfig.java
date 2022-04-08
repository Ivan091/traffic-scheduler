package edu.scheduling.loop;

import edu.config.SchedulingProps;
import edu.model.TimeRange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;


@Configuration
public class LoopServiceConfig {

    @Bean
    public LoopWorkerFactory loopWorkerRealTimeFactory(TaskScheduler taskScheduler, LoopWorkerService loopWorkerService) {
        return (origin, handler) -> new LoopWorkerRealTime(origin, handler, taskScheduler, loopWorkerService);
    }

    @Bean
    public LoopWorkerFactory loopWorkerBatchFactory(TaskExecutor taskExecutor, LoopWorkerService loopWorkerService, SchedulingProps schedulingProps) {
        var range = new TimeRange(schedulingProps.beginDate, schedulingProps.endDate);
        return (origin, handler) -> new LoopWorkerBatch(origin, handler, taskExecutor, loopWorkerService, range);
    }
}

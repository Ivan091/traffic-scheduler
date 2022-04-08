package edu.scheduling.handler;

import edu.model.intensity.SchedulingIntensities;
import edu.model.order.Order;
import edu.repo.OrderRepo;
import edu.scheduling.SchedulingHandler;
import edu.service.PathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Slf4j
@Service
public class DBHandler implements SchedulingHandler {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private PathService pathService;

    @Override
    public void accept(SchedulingIntensities schedulingIntensities, LocalDateTime localDateTime) {
        log.trace("Planned to {} ", localDateTime);
        taskScheduler.schedule(() -> {
            var order = Order.of(pathService.generatePath(schedulingIntensities), 1, localDateTime);
            var savedOrder = orderRepo.save(order);
            log.info("Saved order {}", savedOrder);
        }, Timestamp.valueOf(localDateTime));
    }
}

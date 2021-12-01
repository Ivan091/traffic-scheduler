package edu.model.scheduling;

import edu.model.intensity.SingleOriginIntensities;
import edu.model.order.Order;
import edu.repo.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;


@Slf4j
@Service
@ConditionalOnBean(RealTimeScheduler.class)
public class RealTimeHandler implements BiConsumer<SingleOriginIntensities, LocalDateTime> {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public void accept(SingleOriginIntensities singleOriginIntensities, LocalDateTime localDateTime) {
        log.info("Planned to {} ", localDateTime);
        taskScheduler.schedule(() -> {
            var order = Order.of(singleOriginIntensities.generatePath(), 1, localDateTime);
            orderRepo.save(order);
        }, Timestamp.valueOf(localDateTime));
    }
}

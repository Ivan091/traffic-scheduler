package edu.scheduling;

import edu.config.SchedulingProps;
import edu.scheduling.scheduler.MultipleDaysScheduler;
import edu.scheduling.scheduler.RealTimeScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public final class ModeSwitcher implements Runnable {

    @Autowired
    MultipleDaysScheduler multipleDaysScheduler;

    @Autowired
    RealTimeScheduler realTimeScheduler;

    @Autowired
    SchedulingProps schedulingProps;

    @Override
    public void run() {
        switch (schedulingProps.mode) {
            case ONE_DAY -> multipleDaysScheduler.run();
            case REAL_TIME -> realTimeScheduler.run();
        }
    }
}

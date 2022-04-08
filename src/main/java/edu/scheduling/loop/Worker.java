package edu.scheduling.loop;

import java.time.LocalDateTime;


public interface Worker extends Runnable {

    void next(LocalDateTime planTime);
}

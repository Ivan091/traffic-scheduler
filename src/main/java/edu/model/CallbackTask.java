package edu.model;

import java.util.TimerTask;

public class CallbackTask extends TimerTask {

    private final Runnable callback;

    public CallbackTask(Runnable callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        callback.run();
    }
}

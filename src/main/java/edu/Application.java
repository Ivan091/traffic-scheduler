package edu;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class Application implements ApplicationRunner {

    private final Runnable orderScheduler;

    public Application(Runnable orderScheduler) {
        this.orderScheduler = orderScheduler;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(ApplicationArguments args) {
        orderScheduler.run();
    }
}

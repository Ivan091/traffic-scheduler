package edu;

import edu.model.scheduler.Scheduler;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        var context = new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run();
        context.getBean(Scheduler.class).run();
    }
}

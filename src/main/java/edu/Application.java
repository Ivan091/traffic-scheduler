package edu;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        var context = new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run();
        context.getBean(Scheduler.class).run();
    }
}

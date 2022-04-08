package edu;

import edu.scheduling.scheduler.BatchProcessingScheduler;
import edu.scheduling.scheduler.RealTimeScheduler;
import edu.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;


@SpringBootApplication
@EnableJdbcRepositories
@EnableCaching
@EnableConfigurationProperties
public class Application implements ApplicationRunner {

    @Autowired
    private BatchProcessingScheduler batchProcessingScheduler;

    @Autowired
    private RealTimeScheduler realTimeScheduler;

    @Autowired
    private CheckService checkService;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run(args);
    }

    @Override
    public void run(ApplicationArguments args) {
        switch (args.getSourceArgs()[0]) {
            case "realTime" -> realTimeScheduler.run();
            case "batch" -> batchProcessingScheduler.run();
            case "check" -> {
                checkService.check();
                applicationContext.close();
            }
        }
    }
}

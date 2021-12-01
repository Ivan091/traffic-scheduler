package edu.model.scheduling;

import edu.model.intensity.SingleOriginIntensities;
import edu.model.order.Order;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.*;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;


@Slf4j
@Service
@Scope("prototype")
@ConditionalOnBean(MultipleDaysScheduler.class)
public final class FileHandler implements BiConsumer<SingleOriginIntensities, LocalDateTime> {

    private String actualName;

    @PostConstruct
    @SneakyThrows
    void defineFileName() {
        actualName = generateFileName();
        try (var writer = new BufferedWriter(new FileWriter(actualName))) {
            writer.append(Order.toCSVHeader());
            writer.newLine();
        }
    }

    @Override
    @SneakyThrows
    public void accept(SingleOriginIntensities singleOriginIntensities, LocalDateTime localDateTime) {
        log.trace("Planned to {} ", localDateTime);
        File file = new File(actualName);
        try (var writer = new BufferedWriter(new FileWriter(file, true))) {
            var order = Order.of(singleOriginIntensities.generatePath(), 1, localDateTime);
            writer.append(order.toCSV());
            writer.newLine();
        }
    }

    private String generateFileName() {
        var num = 1;
        var baseName = "data";
        var extension = ".csv";
        var name = baseName + extension;
        var file = new File(name);
        while (file.exists()) {
            name = baseName + (num++) + extension;
            file = new File(name);
        }
        return name;
    }
}

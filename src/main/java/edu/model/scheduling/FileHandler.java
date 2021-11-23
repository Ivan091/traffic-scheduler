package edu.model.scheduling;

import edu.model.intensity.SingleOriginIntensities;
import edu.model.order.Order;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import java.io.*;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;


@Slf4j
@Service
@Scope("prototype")
@ConditionalOnProperty(name = "scheduling.mode", havingValue = "one_day")
public final class FileHandler implements BiConsumer<SingleOriginIntensities, LocalDateTime> {

    private boolean wasWritten = false;

    private String actualName;

    @Override
    @SneakyThrows
    public void accept(SingleOriginIntensities singleOriginIntensities, LocalDateTime localDateTime) {
        if (!wasWritten) {
            prepareToWrite();
            wasWritten = true;
        }
        log.trace("Planned to {} ", localDateTime);
        File file = new File(actualName);
        try (var writer = new BufferedWriter(new FileWriter(file, true))) {
            var order = Order.of(singleOriginIntensities.generatePath(), 1, localDateTime);
            writer.append(order.toCSV());
            writer.newLine();
        }
    }

    @SneakyThrows
    private void prepareToWrite() {
        actualName = generateFileName();
        try (var writer = new BufferedWriter(new FileWriter(actualName))) {
            writer.append(Order.toCSVHeader());
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

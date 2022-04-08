package edu.scheduling.handler;

import edu.model.intensity.SchedulingIntensities;
import edu.model.order.Order;
import edu.scheduling.SchedulingHandler;
import edu.service.OrderService;
import edu.service.PathService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PreDestroy;
import java.io.*;
import java.time.LocalDateTime;


@Slf4j
@Service
public class CSVFileHandler implements SchedulingHandler, Closeable {

    @Autowired
    private PathService pathService;

    @Autowired
    private OrderService orderService;

    private String actualName;

    private boolean isCreated;

    private BufferedWriter writer;

    @SneakyThrows
    void defineFileName() {
        actualName = generateFileName();
        try (var writer = new BufferedWriter(new FileWriter(actualName))) {
            writer.append(orderService.toCsvHeader());
            writer.newLine();
        }
        isCreated = true;
    }

    @Override
    @SneakyThrows
    public synchronized void accept(SchedulingIntensities singleOriginIntensities, LocalDateTime localDateTime) {
        if (!isCreated) {
            defineFileName();
            writer = new BufferedWriter(new FileWriter(actualName, true));
        }
        log.trace("Planned to {} ", localDateTime);
        var order = Order.of(pathService.generatePath(singleOriginIntensities), 1, localDateTime);
        writer.append(orderService.toCsv(order));
        writer.newLine();
    }

    @Override
    @PreDestroy
    @SneakyThrows
    public void close() {
        writer.close();
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

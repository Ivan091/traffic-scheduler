package edu.service;

import edu.repo.OrderRepo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.Objects;
import java.util.regex.Pattern;


@Service
@Slf4j
public final class CheckService {

    private final Pattern pattern = Pattern.compile("Saved order Order\\(id=\\d*,");

    @Autowired
    private OrderRepo orderRepo;

    @SneakyThrows
    public void checkFile(File file) {
        var br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            var matcher = pattern.matcher(st);
            while (matcher.find()) {
                var start = matcher.start();
                var end = matcher.end();
                var sub = st.substring(start + 21, end - 1);
                var id = Integer.valueOf(sub);
                if (orderRepo.existsById(id)) {
                    log.info("Found order with id={}", id);
                } else {
                    log.error("Was not found order {}", st.substring(start + 12));
                }
            }
        }
    }

    public void check() {
        checkFile(new File("logs/logs.log"));
        for (var file : Objects.requireNonNull(new File("logs/archived").listFiles())) {
            checkFile(file);
        }
    }
}

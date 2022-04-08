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
public class CheckService {

    private final Pattern pattern = Pattern.compile("Saved order Order\\(id=\\d*,");

    @Autowired
    private OrderRepo orderRepo;

    @SneakyThrows
    public void checkFile(File file) {
        log.info("Scanning file: {}", file.getName());
        var br = new BufferedReader(new FileReader(file));
        String st;
        var successCount = 0;
        var errorCount = 0;
        while ((st = br.readLine()) != null) {
            var matcher = pattern.matcher(st);
            while (matcher.find()) {
                var start = matcher.start();
                var end = matcher.end();
                var sub = st.substring(start + 21, end - 1);
                var id = Integer.valueOf(sub);
                if (orderRepo.existsById(id)) {
                    successCount++;
                } else {
                    errorCount++;
                    log.error("Was not found in DB {}", st.substring(start + 12));
                }
            }
        }
        log.info("File {} success count = {}", file.getName(), successCount);
        if (errorCount > 0) {
            log.error("File {} error count = {}", file.getName(), errorCount);
        }
    }

    public void check() {
        checkDir(new File("logs"));
    }

    private void checkDir(File file) {
        if (file.isDirectory()) {
            for (var curFile : Objects.requireNonNull(file.listFiles())) {
                checkDir(curFile);
            }
        } else {
            checkFile(file);
        }
    }
}

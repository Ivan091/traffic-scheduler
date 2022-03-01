//package edu.check;
//
//import edu.repo.OrderRepo;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.*;
//import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.util.regex.Pattern;
//
//
//@Slf4j
//@SpringBootApplication
//@ImportAutoConfiguration(OrderRepo.class)
//@EnableJdbcRepositories
//@EnableConfigurationProperties
//public class Check implements ApplicationRunner {
//
//    @Autowired
//    OrderRepo orderRepo;
//
//    @SneakyThrows
//    public static void main(String[] args) {
//        new SpringApplicationBuilder(Check.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
//    }
//
//    @Override
//    @SneakyThrows
//    public void run(ApplicationArguments args) {
//        var pattern = Pattern.compile("Order\\(id=\\d*,");
//        var br = new BufferedReader(new FileReader("logs/logs.log"));
//        String st;
//        while ((st = br.readLine()) != null) {
//            var matcher = pattern.matcher(st);
//            while (matcher.find()) {
//                var start = matcher.start();
//                var end = matcher.end();
//                var sub = st.substring(start + 9, end - 1);
//                System.out.println(sub);
//            }
//        }
//    }
//}

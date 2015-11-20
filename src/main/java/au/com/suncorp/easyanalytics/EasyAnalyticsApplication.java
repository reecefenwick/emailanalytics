package au.com.suncorp.easyanalytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EasyAnalyticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyAnalyticsApplication.class, args);
    }
}

package pl.kurs.mmiaso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class CarParkAppTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarParkAppTestApplication.class, args);
    }

}

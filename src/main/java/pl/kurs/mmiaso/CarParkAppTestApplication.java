package pl.kurs.mmiaso;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.kurs.mmiaso.car.CarRepository;
import pl.kurs.mmiaso.car.model.Car;
import pl.kurs.mmiaso.garage.GarageRepository;
import pl.kurs.mmiaso.garage.GarageService;
import pl.kurs.mmiaso.garage.model.Garage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class CarParkAppTestApplication {
//
//    @Autowired
//    private GarageService dbService;
//    @Autowired
//    private CarRepository carRepository;
//    @Autowired
//    private GarageRepository garageRepository;

    public static void main(String[] args) {
        SpringApplication.run(CarParkAppTestApplication.class, args);
    }

//    @PostConstruct
//    public void run() throws InterruptedException {
//        Garage garage = Garage.builder().capacity(1).build();
//        garageRepository.save(garage);
//
//        Car car = Car.builder().build();
//        carRepository.save(car);
//
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        executor.execute(() -> {
//            try {
//                Thread.sleep(1000);
//                dbService.assignCar(1, 1);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//        executor.execute(() -> {
//            try {
//                Thread.sleep(1000);
//                dbService.assignCar(1, 1);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//        executor.shutdown();
//
//    }

}

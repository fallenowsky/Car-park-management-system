package pl.kurs.mmiaso.car;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.mmiaso.car.model.Car;
import pl.kurs.mmiaso.car.model.dto.CarDto;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor

public class CarService {
    private final CarRepository carRepository;

    public Car findMostExpensive() {
        return carRepository.findMostExpensive();
    }

    public BigDecimal findCarsAveragePrice() {
        return carRepository.findCarsAveragePrice();
    }

    public void save(CarDto carDto, long garageId, long fuelId) {

    }
}

package pl.kurs.mmiaso.car;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.mmiaso.car.exception.GarageIsFullWithCarsException;
import pl.kurs.mmiaso.car.exception.GarageNotHandleLpgException;
import pl.kurs.mmiaso.car.exception.GaragePlaceIsTooNarrowException;
import pl.kurs.mmiaso.car.model.Car;
import pl.kurs.mmiaso.car.model.dto.CarDto;
import pl.kurs.mmiaso.fuel.FuelRepository;
import pl.kurs.mmiaso.fuel.model.Fuel;
import pl.kurs.mmiaso.garage.GarageRepository;
import pl.kurs.mmiaso.garage.model.Garage;

import java.math.BigDecimal;
import java.text.MessageFormat;

@Service
@RequiredArgsConstructor

public class CarService {
    private final CarRepository carRepository;
    private final GarageRepository garageRepository;
    private final FuelRepository fuelRepository;

    public Car findMostExpensive() {
        return carRepository.findMostExpensive();
    }

    public BigDecimal findCarsAveragePrice() {
        return carRepository.findCarsAveragePrice();
    }

    public void save(CarDto carDto, long garageId, long fuelId) {
        Car car = CarDto.dtoToEntity(carDto);

        Garage garage = garageRepository.findByIdWithCarsJoin(garageId)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Garage with id={0} not found", garageId)));

        Fuel fuel = fuelRepository.findById(fuelId)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Fuel with id={0} not found", fuelId)));

        car.setFuel(fuel);
        validateGarageConstrains(garage, car);
        car.setGarage(garage);

        carRepository.save(car);
    }

    private void validateGarageConstrains(Garage garage, Car car) {
        int maxPlaces = garage.getCapacity();
        int takenPlaces = garage.getCars().size();

        if (takenPlaces == maxPlaces + 1) {
            throw new GarageIsFullWithCarsException("This garage is full!");
        }

        if (car.getFuel().getName().equalsIgnoreCase("LPG") && !garage.isLpgAllowed()) {
            throw new GarageNotHandleLpgException("In this garage lpg is not allowed!");
        }

        if (car.getWidth() > garage.getPlaceWidth()) {
            throw new GaragePlaceIsTooNarrowException("Garage place is too narrow for your car!");
        }
    }
}

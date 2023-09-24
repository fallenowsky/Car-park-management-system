package pl.kurs.mmiaso.car;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.mmiaso.car.model.Car;
import pl.kurs.mmiaso.car.model.command.CreateCarCommand;
import pl.kurs.mmiaso.car.model.dto.CarDto;
import pl.kurs.mmiaso.fuel.FuelRepository;
import pl.kurs.mmiaso.fuel.model.Fuel;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final FuelRepository fuelRepository;


    public List<CarDto> findAllWithFuelJoin() {
        return carRepository.findAllWithFuelJoinAndGarageAddressJoin().stream()
                .map(CarDto::entityToDtoWithFuelAndGarage)
                .toList();
    }

    public CarDto findByIdWIthFuelJoin(long carId) {
        Car car = carRepository.findByIdWithFuelJoin(carId)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Car with id={0} not found", carId)));

        return CarDto.entityToDtoWithFuel(car);
    }

    @Transactional
    public void save(CreateCarCommand command, long fuelId) {
        Car car = CreateCarCommand.commandToEntity(command);

        Fuel fuel = fuelRepository.findById(fuelId)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Fuel with id={0} not found", fuelId)));
        car.setFuel(fuel);
        carRepository.save(car);
    }

    public List<CarDto> findCarsByGarageId(long garageId) {
        return carRepository.findAllByGarageIdWithFuelJoin(garageId).stream()
                .map(CarDto::entityToDtoWithFuel)
                .toList();
    }

}

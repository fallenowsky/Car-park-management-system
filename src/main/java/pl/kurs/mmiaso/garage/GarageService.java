package pl.kurs.mmiaso.garage;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import pl.kurs.mmiaso.address.model.Address;
import pl.kurs.mmiaso.address.model.command.CreateAddressCommand;
import pl.kurs.mmiaso.car.CarRepository;
import pl.kurs.mmiaso.car.exceptions.GarageIsFullWithCarsException;
import pl.kurs.mmiaso.car.exceptions.GarageNotHandleLpgException;
import pl.kurs.mmiaso.car.exceptions.GaragePlaceIsTooNarrowException;
import pl.kurs.mmiaso.car.exceptions.MaxOptimisticTriesExceededException;
import pl.kurs.mmiaso.car.model.Car;
import pl.kurs.mmiaso.car.model.dto.CarDto;
import pl.kurs.mmiaso.fuel.FuelRepository;
import pl.kurs.mmiaso.fuel.model.Fuel;
import pl.kurs.mmiaso.fuel.model.dto.FuelDto;
import pl.kurs.mmiaso.garage.model.Garage;
import pl.kurs.mmiaso.garage.model.command.CreateGarageCommand;
import pl.kurs.mmiaso.garage.model.dto.GarageDto;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GarageService {
    private final GarageRepository garageRepository;
    private final CarRepository carRepository;
    private final FuelRepository fuelRepository;

    /* generuje wieksza ilosc zapytan sql, ale znacznie oszczedzam pamiec i czas odpowiedzi
     * pobieram tylko to co mi jest potrzebne, bez powiazanych encji
     * dane potrzebne do obliczen moze mi zwrocic baza, nie potrzebuje miec tego w pamieci aplikacji(całych obiektów)
     * gdyby bylo milion garazy i kazdy ma 1000 aut to bez sensu to wszystko tu ladowac  */


    public List<GarageDto> findAllWithDetails() {
        List<GarageDto> garageDtos = garageRepository.findALlWithAddressJoin().stream()
                .filter(Objects::nonNull)
                .map(GarageDto::entityToDto)
                .toList();

        for (GarageDto garageDto : garageDtos) {
            garageDto.setMostUsedFuel(findMostUsedFuel(garageDto.getId()));
            garageDto.setMostExpensiveCar(findMostExpensiveCar(garageDto.getId()));
            garageDto.setAvgCarsAmount(garageRepository.findGarageAverageCarsPriceById(garageDto.getId()));
            int garageCarsAmount = garageRepository.findGarageCarsAmountById(garageDto.getId());
            garageDto.setFillFactor(((double) garageCarsAmount / garageDto.getCapacity()) * 100);
        }
        return garageDtos;
    }

    private FuelDto findMostUsedFuel(long garageId) {
        Fuel fuel = fuelRepository.findMostUsedFuelByGarageId(garageId)
                .orElse(new Fuel());
        return FuelDto.entityToDto(fuel);
    }

    private CarDto findMostExpensiveCar(long garageId) {
        Car car = carRepository.findMostExpensiveCarByGarageId(garageId)
                .orElse(new Car());
        return CarDto.entityToFlatDto(car);
    }

    public List<GarageDto> findAll() {
        return garageRepository.findALlWithAddressJoin().stream()
                .map(GarageDto::entityToDto)
                .toList();
    }

    public void save(CreateGarageCommand garageCommand, CreateAddressCommand addressCommand) {
        Garage garage = CreateGarageCommand.commandToEntity(garageCommand);
        Address address = CreateAddressCommand.commandToEntity(addressCommand);

        garage.setAddress(address);
        garageRepository.save(garage);
    }

    @Transactional
    public void assignCar(long garageId, long carId) {
        Garage garage = garageRepository.findWithLockingById(garageId) // do pilnowania capacity
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Garage with id={0} not found", garageId)));

        Car car = carRepository.findByIdWithFuelJoin(carId)      // gdyby ktos w miedzy czasie usunal to auto
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Car with id={0} not found", carId)));

        validateCarGarageConstrains(garage, car);
        car.setGarage(garage);

        // tu leci  wyjatek OptimisticLockException gdy version obiektu i w bd sie nie zgadzaja
        // OptimisticLockException jest typu unchecked wiec jest rollback transakcji
        // lapie go w kontrolerze i renderuje 429. jesli nie ma to commit, czyli version sa takie same
        garageRepository.save(garage);
    }

    private void validateCarGarageConstrains(Garage garage, Car car) {
        int maxPlaces = garage.getCapacity();
        int takenPlaces = garageRepository.findGarageCarsAmountById(garage.getId());

        if (takenPlaces == maxPlaces) {
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

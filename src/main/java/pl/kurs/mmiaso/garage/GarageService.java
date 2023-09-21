package pl.kurs.mmiaso.garage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.mmiaso.address.model.Address;
import pl.kurs.mmiaso.address.model.command.CreateAddressCommand;
import pl.kurs.mmiaso.car.CarRepository;
import pl.kurs.mmiaso.car.model.Car;
import pl.kurs.mmiaso.car.model.dto.CarDto;
import pl.kurs.mmiaso.fuel.model.Fuel;
import pl.kurs.mmiaso.fuel.model.dto.FuelDto;
import pl.kurs.mmiaso.garage.model.Garage;
import pl.kurs.mmiaso.garage.model.command.CreateGarageCommand;
import pl.kurs.mmiaso.garage.model.dto.GarageDto;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GarageService {
    private final GarageRepository garageRepository;
    private final CarRepository carRepository;

    /*generuje wieksza ilosc zapytan sql, ale znacznie oszczedzam pamiec i czas odpowiedzi
     * pobieram tylko to co mi jest potrzebne, bez powiazanych encji
     * dane potrzebne do obliczen moze mi zwrocic baza, nie potrzebuje miec tego w pamieci aplikacji(całych obiektów)
     * gdyby bylo milion garazy i kazdy ma 1000 aut to bez sensu to wszystko tu ladowac  */


    public List<GarageDto> findAll() {
        List<GarageDto> garageDtos = garageRepository.findALlWithAddressJoin().stream()
                .filter(Objects::nonNull)
                .map(GarageDto::entityToDto)
                .toList();

        for (GarageDto garageDto : garageDtos) {
            garageDto.setMostUsedFuel(findMostUsedFuel(garageDto.getId()));
            garageDto.setMostExpensiveCar(findMostExpensiveCar(garageDto.getId()));
            garageDto.setAvgCarsAmount(carRepository.findGarageAverageCarsPriceByGarageId(garageDto.getId()));
            int garageCarsAmount = carRepository.findCarsAmountByGarageId(garageDto.getId());
            garageDto.setFillFactor(((double) garageCarsAmount / garageDto.getCapacity()) * 100);
        }
        return garageDtos;
    }

    private FuelDto findMostUsedFuel(long garageId) {
        Fuel fuel = carRepository.findMostUsedFuelByGarageId(garageId)
                .orElse(new Fuel());
        return FuelDto.entityToDto(fuel);
    }

    private CarDto findMostExpensiveCar(long garageId) {
        Car car = carRepository.findMostExpensiveCarByGarageId(garageId)
                .orElse(new Car());
        return CarDto.entityToFlatDto(car);
    }

    public void save(CreateGarageCommand garageCommand, CreateAddressCommand addressCommand) {
        Garage garage = CreateGarageCommand.commandToEntity(garageCommand);
        Address address = CreateAddressCommand.commandToEntity(addressCommand);

        garage.setAddress(address);
        garageRepository.save(garage);
    }
}

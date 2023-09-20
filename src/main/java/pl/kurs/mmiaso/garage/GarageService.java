package pl.kurs.mmiaso.garage;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.mmiaso.address.model.Address;
import pl.kurs.mmiaso.address.model.dto.AddressDto;
import pl.kurs.mmiaso.car.CarRepository;
import pl.kurs.mmiaso.car.model.Car;
import pl.kurs.mmiaso.car.model.dto.CarDto;
import pl.kurs.mmiaso.fuel.model.Fuel;
import pl.kurs.mmiaso.fuel.model.dto.FuelDto;
import pl.kurs.mmiaso.garage.model.Garage;
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
                .map(GarageDto::entityToDtoWithAddress)
                .toList();

        for (GarageDto garageDto : garageDtos) {
            Fuel fuel = carRepository.findMostCommonFuelByGarageId(garageDto.getId())
                            .orElse(new Fuel());
            garageDto.setMostUsedFuel(FuelDto.entityToDto(fuel));
            garageDto.setMostExpensiveCar(findMostExpensiveCar(garageDto));
            garageDto.setAvgCarsAmount(carRepository.findGarageAverageCarsPriceByGarageId(garageDto.getId()));
            int garageCarsAmount = carRepository.findCarsAmountByGarageId(garageDto.getId());
            garageDto.setFillFactor(((double) garageCarsAmount / garageDto.getCapacity()) * 100);
        }

        return garageDtos;
    }

    private CarDto findMostExpensiveCar(GarageDto garageDto) {
        Car car = carRepository.findMostExpensiveCarByGarageId(garageDto.getId())
                .orElse(new Car());
        return car.getFuel() == null ? CarDto.entityToFlatDto(car) : CarDto.entityToDtoWithFuel(car);
    }

    @Transactional
    public void save(GarageDto garageDto, AddressDto addressDto) {
        Garage garage = GarageDto.dtoToFlatEntity(garageDto);
        Address address = AddressDto.dtoToEntity(addressDto);

        garage.setAddress(address);
        garageRepository.save(garage);
    }
}

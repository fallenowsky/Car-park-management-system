package pl.kurs.mmiaso.garage;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.mmiaso.address.model.Address;
import pl.kurs.mmiaso.address.model.dto.AddressDto;
import pl.kurs.mmiaso.car.CarRepository;
import pl.kurs.mmiaso.car.model.Car;
import pl.kurs.mmiaso.car.model.dto.CarDto;
import pl.kurs.mmiaso.garage.model.Garage;
import pl.kurs.mmiaso.garage.model.dto.GarageDto;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor

public class GarageService {
    private final GarageRepository garageRepository;
    private final CarRepository carRepository;

    public List<GarageDto> findAll() {
        List<GarageDto> garageDtos = garageRepository.findALlWithAddressAndCarsJoin().stream()
                .filter(Objects::nonNull)
                .map(GarageDto::entityToDtoWithAddressAndCars)
                .toList();

        for (GarageDto garage : garageDtos) {
            garage.setMostExpensiveCar(findMostExpensiveCar(garage));
            garage.setAvgCarAmount(carRepository.findGarageCarsAveragePrice(garage.getId()));
            garage.setFillFactor(((double)garage.getCarsAmount() / garage.getCapacity()) * 100);
        }

        return garageDtos;
    }

    private CarDto findMostExpensiveCar(GarageDto garageDto) {
        Car car = carRepository.findMostExpensive(garageDto.getId())
                        .orElse(new Car());
        return car.getFuel() == null? CarDto.entityToFlatDto(car) : CarDto.entityToDtoWithFuel(car);
    }

    public void save(GarageDto garageDto, AddressDto addressDto) {
        Garage garage = GarageDto.dtoToFlatEntity(garageDto);
        Address address = AddressDto.dtoToEntity(addressDto);
        garage.setAddress(address);

        garageRepository.save(garage);
    }

    public GarageDto findById(long garageId) {
        Garage garage = garageRepository.findByIdWithAddressAndCarsJoin(garageId)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Garage with id={0} not found", garageId)));

        return GarageDto.entityToDtoWithAddressAndCars(garage);
    }
}

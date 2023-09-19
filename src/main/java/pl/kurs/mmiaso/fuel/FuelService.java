package pl.kurs.mmiaso.fuel;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.mmiaso.fuel.exceptions.ThisFuelAlreadyExists;
import pl.kurs.mmiaso.fuel.model.Fuel;
import pl.kurs.mmiaso.fuel.model.dto.FuelDto;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor

public class FuelService {
    private final FuelRepository fuelRepository;

    public List<Fuel> findAll() {
        return fuelRepository.findAll();
    }

    public void save(FuelDto fuelDto) {
        Fuel newFuel = FuelDto.dtoToEntity(fuelDto);
        List<Fuel> fuels = fuelRepository.findAll();

        for (Fuel fuel : fuels) {
            if (fuel.getName().equalsIgnoreCase(newFuel.getName())) {
                throw new ThisFuelAlreadyExists("Fuel you want to add is already added in the application!");
            }
        }

        fuelRepository.save(newFuel);
    }

    @PostConstruct
    public void saveInitialFuels() {
        Fuel petrol = new Fuel(1L, "Petrol");
        Fuel hybrid = new Fuel(3L, "Hybrid");
        Fuel electric = new Fuel(4L, "Electric");

        fuelRepository.saveAll(Arrays.asList(petrol, hybrid, electric));
    }
}

package pl.kurs.mmiaso.fuel;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.mmiaso.fuel.exceptions.ThisFuelAlreadyExists;
import pl.kurs.mmiaso.fuel.model.Fuel;
import pl.kurs.mmiaso.fuel.model.dto.FuelDto;

import java.util.Arrays;
import java.util.List;

/* podczas startu aplikacji dodaje kilka defaultowych paliw jakie apka obsluguje
 *  wychodze jednak z zalozenia, że moze powstac jakies nowe paliwo lub moge chciec obslugiwac diesla
 *  wtedy moge rozszerzyc aplikacje i go w latwy sposob dodac */

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
        Fuel hybrid = new Fuel(2L, "Hybrid");
        Fuel electric = new Fuel(3L, "Electric");

        fuelRepository.saveAll(Arrays.asList(petrol, hybrid, electric));
    }
}

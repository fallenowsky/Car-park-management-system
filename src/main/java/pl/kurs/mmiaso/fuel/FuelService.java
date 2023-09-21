package pl.kurs.mmiaso.fuel;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.mmiaso.fuel.exceptions.ThisFuelAlreadyExists;
import pl.kurs.mmiaso.fuel.model.Fuel;
import pl.kurs.mmiaso.fuel.model.dto.FuelDto;
import pl.kurs.mmiaso.fuel.model.command.CreateFuelCommand;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* podczas startu aplikacji dodaje kilka defaultowych paliw jakie apka obsluguje
 *  wychodze jednak z zalozenia, że moze powstac jakies nowe paliwo lub moge chciec obslugiwac diesla
 *  wtedy moge rozszerzyc aplikacje i go w latwy sposob dodac */

@Service
@RequiredArgsConstructor
public class FuelService {
    private final FuelRepository fuelRepository;

    public List<FuelDto> findAll() {
        return fuelRepository.findAll().stream()
                .filter(Objects::nonNull)
                .map(FuelDto::entityToDto)
                .toList();
    }

    public void save(CreateFuelCommand command) {
        Fuel newFuel = CreateFuelCommand.commandToEntity(command);
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
        Fuel petrol = Fuel.builder().name("Petrol").build();
        Fuel hybrid = Fuel.builder().name("Hybrid").build();
        Fuel electric = Fuel.builder().name("Electric").build();

        fuelRepository.saveAll(Arrays.asList(petrol, hybrid, electric));
    }
}

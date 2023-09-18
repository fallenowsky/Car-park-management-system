package pl.kurs.mmiaso.fuel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.mmiaso.fuel.model.Fuel;

import java.util.List;

@Service
@RequiredArgsConstructor

public class FuelService {
    private final FuelRepository fuelRepository;

    public List<Fuel> findAll() {
        return fuelRepository.findAll();
    }
}

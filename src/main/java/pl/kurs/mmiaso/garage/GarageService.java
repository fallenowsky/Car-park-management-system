package pl.kurs.mmiaso.garage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.mmiaso.garage.model.Garage;
import pl.kurs.mmiaso.garage.model.dto.GarageDto;

import java.util.Set;

@Service
@RequiredArgsConstructor

public class GarageService {
    private final GarageRepository garageRepository;

    public Set<Garage> findAll() {
        return garageRepository.findALlWithAddressAndCarsJoin();
    }

    public void save(GarageDto garageDto) {
        Garage garage = GarageDto.dtoToEntity(garageDto);
        garageRepository.save(garage);
    }
}

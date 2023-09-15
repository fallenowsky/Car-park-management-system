package pl.kurs.mmiaso.garage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.mmiaso.garage.model.Garage;
import pl.kurs.mmiaso.garage.model.dto.GarageDto;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor

public class GarageService {
    private final GarageRepository garageRepository;

    public List<GarageDto> findAll() {
        List<Garage> garages = garageRepository.findALlWithAddressAndCarsJoin();

        return garages.stream()
                .filter(Objects::nonNull)
                .map(GarageDto::entityToDto)
                .toList();
    }

    public void save(GarageDto garageDto) {
        Garage garage = GarageDto.dtoToEntity(garageDto);
        garageRepository.save(garage);
    }


}

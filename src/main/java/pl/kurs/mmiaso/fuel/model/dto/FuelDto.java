package pl.kurs.mmiaso.fuel.model.dto;

import lombok.Builder;
import lombok.Data;
import pl.kurs.mmiaso.fuel.model.Fuel;

@Data
@Builder

public class FuelDto {
    private Long id;
    private String name;

    public static FuelDto entityToDto(Fuel fuel) {
        return FuelDto.builder()
                .id(fuel.getId())
                .name(fuel.getName())
                .build();
    }
}

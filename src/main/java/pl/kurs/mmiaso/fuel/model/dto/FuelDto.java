package pl.kurs.mmiaso.fuel.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import pl.kurs.mmiaso.fuel.model.Fuel;

@Data
@Builder

public class FuelDto {
    @NotBlank
    private String name;

    public static Fuel dtoToEntity(FuelDto fuelDto) {
        return Fuel.builder()
                .name(fuelDto.getName())
                .build();
    }

    public static FuelDto entityToDto(Fuel fuel) {
        return FuelDto.builder()
                .name(fuel.getName())
                .build();
    }
}

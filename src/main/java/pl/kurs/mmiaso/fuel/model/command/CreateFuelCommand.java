package pl.kurs.mmiaso.fuel.model.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import pl.kurs.mmiaso.fuel.model.Fuel;

@Data
@Builder
public class CreateFuelCommand {
    @NotBlank
    private String name;

    public static Fuel commandToEntity(CreateFuelCommand command) {
        return Fuel.builder()
                .name(command.getName())
                .build();
    }
}

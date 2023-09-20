package pl.kurs.mmiaso.garage.model.command;

import jakarta.validation.constraints.*;
import lombok.Data;
import pl.kurs.mmiaso.garage.model.Garage;

@Data
public class CreateGarageCommand {
    @Min(1)
    @Max(Integer.MAX_VALUE)
    private int capacity;
    @NotNull
    private boolean isLpgAllowed;
    @DecimalMin("2.5")
    @DecimalMax("10.0")
    private double placeWidth;

    public static Garage commandToEntity(CreateGarageCommand command) {
        return Garage.builder()
                .capacity(command.getCapacity())
                .isLpgAllowed(command.isLpgAllowed())
                .placeWidth(command.getPlaceWidth())
                .build();
    }
}

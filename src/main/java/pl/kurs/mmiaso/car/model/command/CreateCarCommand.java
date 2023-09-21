package pl.kurs.mmiaso.car.model.command;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import pl.kurs.mmiaso.car.model.Car;

import java.math.BigDecimal;

@Data
@Builder
public class CreateCarCommand {
    @NotBlank
    private String brand;
    @DecimalMin("1.0")
    @DecimalMax("10.0")
    private double width;
    @Positive
    private BigDecimal price;

    public static Car commandToEntity(CreateCarCommand command) {
        return Car.builder()
                .brand(command.getBrand())
                .width(command.getWidth())
                .price(command.getPrice())
                .build();
    }

}

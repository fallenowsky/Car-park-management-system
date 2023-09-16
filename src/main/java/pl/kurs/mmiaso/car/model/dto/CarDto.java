package pl.kurs.mmiaso.car.model.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.kurs.mmiaso.car.model.Car;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@ToString

public class CarDto {
    @NotNull
    private String brand;
    @DecimalMin("1.0")
    @DecimalMax("10.0")
    private double width;
    @Positive
    private BigDecimal price;

    public static Car dtoToEntity(CarDto carDto) {
        return Car.builder()
                .brand(carDto.getBrand())
                .width(carDto.getWidth())
                .price(carDto.getPrice())
                .build();
    }
}

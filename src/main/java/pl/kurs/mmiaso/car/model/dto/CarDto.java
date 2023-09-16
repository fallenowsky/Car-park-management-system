package pl.kurs.mmiaso.car.model.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.kurs.mmiaso.car.model.Car;

import java.math.BigDecimal;

@Builder
@Data

public class CarDto {
    @NotNull
    private String brand;
    @DecimalMin("1.0")
    @DecimalMax("10.0")
    private double width;
    @Positive
    private BigDecimal price;
    private String fuelName;

    public static Car dtoToEntity(CarDto carDto) {
        return Car.builder()
                .brand(carDto.getBrand())
                .width(carDto.getWidth())
                .price(carDto.getPrice())
                .build();
    }

    public static CarDto entityToDtoWithFuel(Car car) {
        return CarDto.builder()
                .brand(car.getBrand())
                .width(car.getWidth())
                .price(car.getPrice())
                .fuelName(car.getFuel().getName())
                .build();
    }

    public static CarDto entityToFlatDto(Car car) {
        return CarDto.builder()
                .brand(car.getBrand())
                .width(car.getWidth())
                .price(car.getPrice())
                .build();
    }
}

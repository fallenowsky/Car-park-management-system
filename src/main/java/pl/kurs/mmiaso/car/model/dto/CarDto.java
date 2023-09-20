package pl.kurs.mmiaso.car.model.dto;

import lombok.Builder;
import lombok.Data;
import pl.kurs.mmiaso.car.model.Car;

import java.math.BigDecimal;

@Builder
@Data
public class CarDto {
    private String brand;
    private double width;
    private BigDecimal price;
    private String fuelName;

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

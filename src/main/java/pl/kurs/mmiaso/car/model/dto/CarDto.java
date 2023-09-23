package pl.kurs.mmiaso.car.model.dto;

import lombok.Builder;
import lombok.Data;
import pl.kurs.mmiaso.car.model.Car;

import java.math.BigDecimal;

@Builder
@Data
public class CarDto {
    private Long id;
    private String brand;
    private double width;
    private BigDecimal price;
    private String fuelName;
    private String garageName;

    public static CarDto entityToDtoWithFuel(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .width(car.getWidth())
                .price(car.getPrice())
                .fuelName(car.getFuel().getName())
                .build();
    }

    public static CarDto entityToDtoWithFuelAndGarage(Car car) {
        if (car.getGarage() != null) {
            return CarDto.builder()
                    .id(car.getId())
                    .brand(car.getBrand())
                    .width(car.getWidth())
                    .price(car.getPrice())
                    .fuelName(car.getFuel().getName())
                    .garageName(car.getGarage().getAddress().getName())
                    .build();
        }
        return entityToDtoWithFuel(car);
    }

    public static CarDto entityToFlatDto(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .width(car.getWidth())
                .price(car.getPrice())
                .build();
    }

}

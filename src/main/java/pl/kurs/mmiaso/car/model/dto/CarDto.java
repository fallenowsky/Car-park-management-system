package pl.kurs.mmiaso.car.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@ToString

public class CarDto {
    private String brand;
    private double width;
    private BigDecimal price;
}

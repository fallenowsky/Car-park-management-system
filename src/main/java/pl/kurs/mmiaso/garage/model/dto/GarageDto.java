package pl.kurs.mmiaso.garage.model.dto;

import lombok.Builder;
import lombok.Data;
import pl.kurs.mmiaso.address.model.dto.AddressDto;
import pl.kurs.mmiaso.car.model.dto.CarDto;
import pl.kurs.mmiaso.fuel.model.dto.FuelDto;
import pl.kurs.mmiaso.garage.model.Garage;

import java.math.BigDecimal;

@Builder
@Data
public class GarageDto {
    private Long id;
    private int capacity;
    private boolean isLpgAllowed;
    private double placeWidth;
    private AddressDto addressDto;
    private FuelDto mostUsedFuel;
    private CarDto mostExpensiveCar;
    private BigDecimal avgCarsAmount;
    private double fillFactor;


    public static GarageDto entityToDto(Garage garage) {
        AddressDto addressDto = AddressDto.entityToDto(garage.getAddress());
        return GarageDto.
                builder()
                .id(garage.getId())
                .capacity(garage.getCapacity())
                .isLpgAllowed(garage.isLpgAllowed())
                .placeWidth(garage.getPlaceWidth())
                .addressDto(addressDto)
                .build();
    }
}

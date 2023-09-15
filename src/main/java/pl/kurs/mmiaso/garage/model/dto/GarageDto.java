package pl.kurs.mmiaso.garage.model.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.kurs.mmiaso.address.model.Address;
import pl.kurs.mmiaso.garage.model.Garage;

@Builder
@Getter
@Setter
@ToString

public class GarageDto {
    private Long id;
    @Min(1)
    @Max(Integer.MAX_VALUE)
    private int capacity;
    private boolean isLpgAllowed;
    @DecimalMin("2.5")
    @DecimalMax("10.0")
    private double placeWidth;
    @NotNull
    private String garageName;
    @NotNull
    private String garageStreet;
    @NotNull
    private String garageZip;
    @NotNull
    private String garageCity;
    @NotNull
    private String garageCountry;
    private int carsAmount;

    public static Garage dtoToEntity(GarageDto garageDto) {
        return Garage.builder()
                .capacity(garageDto.getCapacity())
                .isLpgAllowed(garageDto.isLpgAllowed())
                .placeWidth(garageDto.getPlaceWidth())
                .address(Address.builder()
                        .name(garageDto.getGarageName())
                        .street(garageDto.getGarageStreet())
                        .zipCode(garageDto.getGarageZip())
                        .city(garageDto.getGarageCity())
                        .country(garageDto.getGarageCountry())
                        .build())
                .build();
    }

    public static GarageDto entityToDto(Garage garage) {
        return GarageDto.
                builder()
                .id(garage.getId())
                .capacity(garage.getCapacity())
                .isLpgAllowed(garage.isLpgAllowed())
                .placeWidth(garage.getPlaceWidth())
                .garageName(garage.getAddress().getName())
                .garageStreet(garage.getAddress().getStreet())
                .garageZip(garage.getAddress().getZipCode())
                .garageCity(garage.getAddress().getCity())
                .garageCountry(garage.getAddress().getCountry())
                .carsAmount(garage.getCars().size())
                .build();
    }
}

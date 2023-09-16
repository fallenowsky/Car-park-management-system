package pl.kurs.mmiaso.address.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import pl.kurs.mmiaso.address.model.Address;

@Builder
@Getter

public class AddressDto {
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String street;
    @NotNull
    @NotBlank
    private String zipCode;
    @NotNull
    @NotBlank
    private String city;
    @NotNull
    @NotBlank
    private String country;

    public static AddressDto entityToDto(Address address) {
        return AddressDto.builder()
                .name(address.getName())
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .city(address.getCity())
                .country(address.getCountry())
                .build();
    }

    public static Address dtoToEntity(AddressDto addressDto) {
        return Address.builder()
                .name(addressDto.getName())
                .street(addressDto.getStreet())
                .zipCode(addressDto.getZipCode())
                .city(addressDto.getCity())
                .country(addressDto.getCountry())
                .build();
    }


}

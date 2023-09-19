package pl.kurs.mmiaso.address.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import pl.kurs.mmiaso.address.model.Address;

@Builder
@Data

public class AddressDto {
    @NotBlank
    private String name;
    @NotBlank
    private String street;
    @NotBlank
    private String zipCode;
    @NotBlank
    private String city;
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

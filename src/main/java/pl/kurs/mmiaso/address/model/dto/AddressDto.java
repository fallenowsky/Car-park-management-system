package pl.kurs.mmiaso.address.model.dto;

import lombok.Builder;
import lombok.Data;
import pl.kurs.mmiaso.address.model.Address;

@Builder
@Data
public class AddressDto {
    private String name;
    private String street;
    private String zipCode;
    private String city;
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
}

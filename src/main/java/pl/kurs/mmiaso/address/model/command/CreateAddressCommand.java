package pl.kurs.mmiaso.address.model.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import pl.kurs.mmiaso.address.model.Address;

@Data
@Builder
public class CreateAddressCommand {
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

    public static Address commandToEntity(CreateAddressCommand command) {
        return Address.builder()
                .name(command.getName())
                .street(command.getStreet())
                .zipCode(command.getZipCode())
                .city(command.getCity())
                .country(command.getCountry())
                .build();
    }
}

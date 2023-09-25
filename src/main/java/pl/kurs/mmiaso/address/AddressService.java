package pl.kurs.mmiaso.address;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.mmiaso.address.model.Address;
import pl.kurs.mmiaso.address.model.dto.AddressDto;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressDto findByGarageId(long garageId) {
        Address address = addressRepository.findByGarageId(garageId)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Address with garage id={0} not found", garageId)));
        return AddressDto.entityToDto(address);
    }

}

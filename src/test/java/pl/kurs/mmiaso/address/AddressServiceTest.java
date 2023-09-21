package pl.kurs.mmiaso.address;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.mmiaso.address.model.Address;
import pl.kurs.mmiaso.address.model.dto.AddressDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private AddressService service;

    @Test
    public void testFindByGarageId_AddressFound_ResultsInAddressReturnedAndMockMethodCall() {
        Address address = Address.builder().id(1L).name("fallenowsky").street("Zlota 44").zipCode("44-233")
                .city("Warszawa").country("Poland").build();
        long garageId = 2L;
        when(addressRepository.findByGarageId(garageId)).thenReturn(Optional.of(address));

        AddressDto returned = service.findByGarageId(garageId);

        assertEquals(AddressDto.entityToDto(address), returned);
        verify(addressRepository).findByGarageId(garageId);
    }

    @Test
    public void testFindByGarageId_AddressNotFound_ResultsInEntityNotFoundException() {
        long garageId = 2;
        String excMessage = "Address with garage id=2 not found";

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> service.findByGarageId(garageId))
                .withMessage(excMessage);

        verify(addressRepository).findByGarageId(garageId);
    }
}
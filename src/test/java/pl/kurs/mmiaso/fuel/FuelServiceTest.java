package pl.kurs.mmiaso.fuel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.mmiaso.fuel.exceptions.ThisFuelAlreadyExists;
import pl.kurs.mmiaso.fuel.model.Fuel;
import pl.kurs.mmiaso.fuel.model.command.CreateFuelCommand;
import pl.kurs.mmiaso.fuel.model.dto.FuelDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FuelServiceTest {
    @Mock
    private FuelRepository fuelRepository;
    @InjectMocks
    private FuelService service;
    @Captor
    private ArgumentCaptor<Fuel> fuelArgumentCaptor;
    private Fuel fuel;
    private Fuel fuel2;

    @BeforeEach
    public void init() {
        fuel = Fuel.builder().id(1L).name("Petrol").build();
        fuel2 = Fuel.builder().id(2L).name("Diesel").build();
    }

    @Test
    public void testFindAll_FuelsFound_ResultsInFuelRepositoryListReturned() {
        List<Fuel> fuels = Arrays.asList(fuel, fuel2);
        when(fuelRepository.findAll()).thenReturn(fuels);

        List<FuelDto> returned = service.findAll();

        assertEquals(returned.get(0), FuelDto.entityToDto(fuel));
        assertEquals(returned.get(1), FuelDto.entityToDto(fuel2));
        verify(fuelRepository).findAll();
    }

    @Test
    public void testFindAll_FuelsNotFound_ResultsInEmptyReturnedFuelListReturned() {
        List<FuelDto> returned = service.findAll();

        assertEquals(Collections.emptyList(), returned);
        verify(fuelRepository).findAll();
    }

    @Test
    public void testSave_MethodInputCorrect_ResultsInMockMethodCallAndFuelCaptor() {
        CreateFuelCommand command = CreateFuelCommand.builder()
                .name("Hybrid")
                .build();
        List<Fuel> fuels = Arrays.asList(fuel, fuel2);
        when(fuelRepository.findAll()).thenReturn(fuels);

        service.save(command);

        verify(fuelRepository).findAll();
        verify(fuelRepository).save(fuelArgumentCaptor.capture());
        verify(fuelRepository).save(any(Fuel.class));
        Fuel fuelCaptured = fuelArgumentCaptor.getValue();
        assertEquals(command.getName(), fuelCaptured.getName());
    }

    @Test
    public void testSave_FuelAlreadyExists_ResultsInThisFuelAlreadyExistsException() {
        CreateFuelCommand command = CreateFuelCommand.builder()
                .name("Petrol")
                .build();
        String excMessage = "Fuel you want to add is already added in the application!";
        List<Fuel> fuels = Arrays.asList(fuel, fuel2);
        when(fuelRepository.findAll()).thenReturn(fuels);

        assertThatExceptionOfType(ThisFuelAlreadyExists.class)
                .isThrownBy(() -> service.save(command))
                .withMessage(excMessage);

        verify(fuelRepository).findAll();
        verifyNoMoreInteractions(fuelRepository);
    }

    @Test
    public void testSave_NoFuelsInDb_ResultsInFuelMockMethodCallAndFuelCapture() {
        CreateFuelCommand command = CreateFuelCommand.builder()
                .name("Petrol")
                .build();

        service.save(command);

        verify(fuelRepository).findAll();
        verify(fuelRepository).save(fuelArgumentCaptor.capture());
        verify(fuelRepository).save(any(Fuel.class));
        Fuel fuelCaptured = fuelArgumentCaptor.getValue();
        assertEquals(command.getName(), fuelCaptured.getName());
    }
}
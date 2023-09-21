package pl.kurs.mmiaso.garage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.mmiaso.address.model.Address;
import pl.kurs.mmiaso.address.model.command.CreateAddressCommand;
import pl.kurs.mmiaso.car.CarRepository;
import pl.kurs.mmiaso.car.model.Car;
import pl.kurs.mmiaso.car.model.dto.CarDto;
import pl.kurs.mmiaso.fuel.FuelRepository;
import pl.kurs.mmiaso.fuel.model.Fuel;
import pl.kurs.mmiaso.fuel.model.dto.FuelDto;
import pl.kurs.mmiaso.garage.model.Garage;
import pl.kurs.mmiaso.garage.model.command.CreateGarageCommand;
import pl.kurs.mmiaso.garage.model.dto.GarageDto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GarageServiceTest {
    @Mock
    private GarageRepository garageRepository;
    @Mock
    private CarRepository carRepository;
    @Mock
    private FuelRepository fuelRepository;
    @InjectMocks
    private GarageService service;
    @Captor
    private ArgumentCaptor<Garage> garageArgumentCaptor;
    private Garage garage;
    private Garage garage2;

    @BeforeEach
    public void init() {
        Address address = Address.builder().id(1L).name("fallenowsky").street("Zlota 44").zipCode("44-233")
                .city("Warszawa").country("Poland").build();
        garage = Garage.builder().id(1L).capacity(3).isLpgAllowed(true).address(address).build();
        garage2 = Garage.builder().id(2L).capacity(5).isLpgAllowed(false).address(address).build();
    }

    @Test
    public void testFindAll_GaragesFound_ResultsInRepositoryGaragesListReturnedAndMockMethodCall() {
        List<Garage> garages = Arrays.asList(garage, garage2);
        long garageId = garage.getId();
        long garage2Id = garage2.getId();
        Fuel petrol = Fuel.builder().id(1L).name("Petrol").build();
        Fuel hybrid = Fuel.builder().id(1L).name("Hybrid").build();
        Car bmw = Car.builder().brand("BMW").width(2.7).price(BigDecimal.valueOf(29292.1)).build();
        Car audi = Car.builder().brand("AUDI").width(2.2).price(BigDecimal.valueOf(54323.2)).build();
        when(garageRepository.findALlWithAddressJoin()).thenReturn(garages);
        when(fuelRepository.findMostUsedFuelByGarageId(garageId)).thenReturn(Optional.of(petrol));
        when(fuelRepository.findMostUsedFuelByGarageId(garage2Id)).thenReturn(Optional.of(hybrid));
        when(carRepository.findMostExpensiveCarByGarageId(garageId)).thenReturn(Optional.of(bmw));
        when(carRepository.findMostExpensiveCarByGarageId(garage2Id)).thenReturn(Optional.of(audi));
        when(garageRepository.findGarageAverageCarsPriceById(garageId)).thenReturn(bmw.getPrice());
        when(garageRepository.findGarageAverageCarsPriceById(garage2Id)).thenReturn(audi.getPrice());
        when(garageRepository.findGarageCarsAmountById(garageId)).thenReturn(2);
        when(garageRepository.findGarageCarsAmountById(garage2Id)).thenReturn(3);

        List<GarageDto> returned = service.findAll();

        for (int i = 0; i < returned.size(); i++) {
            GarageDto garage = returned.get(i);

            if (i == 0) {
                assertEquals(garage.getMostUsedFuel(), FuelDto.entityToDto(petrol));
                assertEquals(garage.getMostExpensiveCar(), CarDto.entityToFlatDto(bmw));
                assertEquals(garage.getAvgCarsAmount(), bmw.getPrice());
                assertEquals(garage.getFillFactor(), 66.6, 0.1);
            } else {
                assertEquals(garage.getMostUsedFuel(), FuelDto.entityToDto(hybrid));
                assertEquals(garage.getMostExpensiveCar(), CarDto.entityToFlatDto(audi));
                assertEquals(garage.getAvgCarsAmount(), audi.getPrice());
                assertEquals(garage.getFillFactor(), 60, 0.1);
            }
        }
        verify(garageRepository).findALlWithAddressJoin();
        verifyNoMoreInteractions(garageRepository);
        verify(fuelRepository).findMostUsedFuelByGarageId(garageId);
        verify(fuelRepository).findMostUsedFuelByGarageId(garage2Id);
        verify(carRepository).findMostExpensiveCarByGarageId(garageId);
        verify(carRepository).findMostExpensiveCarByGarageId(garage2Id);
        verify(garageRepository).findGarageAverageCarsPriceById(garageId);
        verify(garageRepository).findGarageAverageCarsPriceById(garage2Id);
        verify(garageRepository).findGarageCarsAmountById(garageId);
        verify(garageRepository).findGarageCarsAmountById(garage2Id);
    }

    @Test
    public void testFindAll_GaragesNotFound_ResultsInEmptyListReturnedAndNoLoopRepeats() {
        List<GarageDto> returned = service.findAll();

        assertEquals(Collections.emptyList(), returned);
        verify(garageRepository).findALlWithAddressJoin();
        verifyNoMoreInteractions(garageRepository);
        verifyNoInteractions(carRepository);
    }


    @Test
    public void testSave_MethodInputsCorrect_ResultsInMockMethodCallAndGarageCapture() {
        CreateGarageCommand garageCommand = CreateGarageCommand.builder()
                .capacity(4)
                .isLpgAllowed(false)
                .placeWidth(3.2)
                .build();
        CreateAddressCommand addressCommand = CreateAddressCommand.builder()
                .name("fallenowsky")
                .street("Złota 44")
                .zipCode("21-321")
                .city("Warszawa")
                .country("Poland")
                .build();
        Garage garage = CreateGarageCommand.commandToEntity(garageCommand);

        service.save(garageCommand, addressCommand);

        verify(garageRepository).save(garageArgumentCaptor.capture());
        Garage garageCaptured = garageArgumentCaptor.getValue();
        assertEquals(garage.getCapacity(), garageCaptured.getCapacity());
        assertEquals(garage.getPlaceWidth(), garageCaptured.getPlaceWidth());
        assertEquals(garage.isLpgAllowed(), garageCaptured.isLpgAllowed());
        verifyNoMoreInteractions(garageRepository);
    }

}
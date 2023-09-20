package pl.kurs.mmiaso.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.mmiaso.address.model.Address;
import pl.kurs.mmiaso.car.model.Car;
import pl.kurs.mmiaso.car.model.dto.CarDto;
import pl.kurs.mmiaso.fuel.FuelRepository;
import pl.kurs.mmiaso.fuel.model.Fuel;
import pl.kurs.mmiaso.garage.GarageRepository;
import pl.kurs.mmiaso.garage.model.Garage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private GarageRepository garageRepository;
    @Mock
    private FuelRepository fuelRepository;
    @InjectMocks
    private CarService service;
    @Captor
    private ArgumentCaptor<Car> carArgumentCaptor;
    private Car car;
    private Car car2;
    private CarDto carDto;
    private CarDto car2Dto;
    private long garageId;

    @BeforeEach
    public void init() {
        Garage garage = new Garage(1L, 3, false, 2.5, 1L,
                new Address(1L, "fallenowsky", "Złota 44", "33-199", "Warszawa", "Polska", Collections.emptySet()),
                Collections.emptySet());
        Garage garage1 = new Garage(2L, 2, true, 3.5, 1L,
                new Address(2L, "quarkowsky", "Srebrna 44", "33-199", "Poznań", "Polska", Collections.emptySet()),
                Collections.emptySet());
        car = new Car(1L, "BMW", 2.3, BigDecimal.valueOf(299000.4), new Fuel(1L, "Petrol"), garage);
        car2 = new Car(2L, "LAMBORGHINI", 2.9, BigDecimal.valueOf(4324212.4), new Fuel(2L, "Electric"), garage1);
        carDto = CarDto.builder()
                .brand("BMW")
                .width(2.3)
                .price(BigDecimal.valueOf(299000.4))
                .fuelName("Petrol")
                .build();

        car2Dto = CarDto.builder()
                .brand("LAMBORGHINI")
                .width(2.9)
                .price(BigDecimal.valueOf(4324212.4))
                .fuelName("Electric")
                .build();
        garageId = 1L;
    }

    @Test
    public void testFindCarsByGarageId_GarageFound_ResultsInRepoCarsReturned() {
        List<Car> expectedCars = Arrays.asList(car, car2);
        List<CarDto> expectedCarsDto = Arrays.asList(carDto, car2Dto);
        when(carRepository.findAllByGarageIdWithFuelJoin(garageId)).thenReturn(expectedCars);

        List<CarDto> returned = service.findCarsByGarageId(garageId);

        assertEquals(expectedCarsDto, returned);
        verify(carRepository).findAllByGarageIdWithFuelJoin(garageId);
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    public void testFindCarsByGarageId_GarageFound_ResultsInRepoCarsIncorrectReturned() {
        List<Car> notExpectedCars = Arrays.asList(car, car2);
        List<CarDto> notExpectedCarsDto = Arrays.asList(carDto);
        when(carRepository.findAllByGarageIdWithFuelJoin(garageId)).thenReturn(notExpectedCars);

        List<CarDto> returned = service.findCarsByGarageId(garageId);

        assertNotEquals(notExpectedCarsDto, returned);
        verify(carRepository).findAllByGarageIdWithFuelJoin(garageId);
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    public void testFindCarsByGarageId_GarageNotFound_ResultsInEmptyListReturned() {
        when(carRepository.findAllByGarageIdWithFuelJoin(garageId)).thenReturn(Collections.emptyList());

        List<CarDto> returned = service.findCarsByGarageId(garageId);

        assertEquals(Collections.emptyList(), returned);
        verify(carRepository).findAllByGarageIdWithFuelJoin(garageId);
        verifyNoMoreInteractions(carRepository);
    }


}
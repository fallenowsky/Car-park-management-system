package pl.kurs.mmiaso.car;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.mmiaso.address.model.Address;
import pl.kurs.mmiaso.car.exceptions.GarageIsFullWithCarsException;
import pl.kurs.mmiaso.car.exceptions.GarageNotHandleLpgException;
import pl.kurs.mmiaso.car.exceptions.GaragePlaceIsTooNarrowException;
import pl.kurs.mmiaso.car.model.Car;
import pl.kurs.mmiaso.car.model.dto.CarDto;
import pl.kurs.mmiaso.fuel.FuelRepository;
import pl.kurs.mmiaso.fuel.model.Fuel;
import pl.kurs.mmiaso.garage.GarageRepository;
import pl.kurs.mmiaso.garage.model.Garage;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    private Garage garage;
    private Garage garage2;
    private Car car;
    private Car car2;
    private Fuel fuel;
    private Fuel fuel2;
    private CarDto carDto;
    private CarDto car2Dto;
    private long garageId;
    private long fuelId;

    @BeforeEach
    public void init() {
        fuel = new Fuel(1L, "Petrol");
        fuel2 = new Fuel(2L, "LPG");
        garage = new Garage(1L, 3, false, 2.5, 1L,
                new Address(1L, "fallenowsky", "Złota 44", "33-199", "Warszawa", "Polska", Collections.emptySet()),
                Collections.emptySet());
        garage2 = new Garage(2L, 2, true, 3.5, 1L,
                new Address(2L, "quarkowsky", "Srebrna 44", "33-199", "Poznań", "Polska", Collections.emptySet()),
                Collections.emptySet());
        car = new Car(1L, "BMW", 2.3, BigDecimal.valueOf(299000.4), fuel, garage);
        car2 = new Car(2L, "LAMBORGHINI", 2.9, BigDecimal.valueOf(4324212.4), fuel2, garage2);
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
                .fuelName("LPG")
                .build();
        garageId = 1L;
        fuelId = 1L;
    }

    @Test
    public void testSave_MethodInputsCorrect_ResultsInMockMethodCallAndCarCapture() {
        when(garageRepository.findWithLockingById(garageId)).thenReturn(Optional.of(garage));
        when(fuelRepository.findById(fuelId)).thenReturn(Optional.of(fuel));
        when(carRepository.findCarsAmountByGarageId(garageId)).thenReturn(2);

        service.save(carDto, garageId, fuelId);

        verify(garageRepository).findWithLockingById(garageId);
        verify(fuelRepository).findById(fuelId);
        verify(carRepository).save(carArgumentCaptor.capture());
        Car carCaptured = carArgumentCaptor.getValue();
        assertEquals(car.getBrand(), carCaptured.getBrand());
        assertEquals(car.getGarage(), carCaptured.getGarage());
        assertEquals(car.getFuel(), carCaptured.getFuel());
        assertEquals(car.getPrice(), carCaptured.getPrice());
        assertEquals(car.getWidth(), carCaptured.getWidth());
    }

    @Test
    public void testSave_GarageIsFull_ResultsInGarageIsFullWithCarsException() {
        String excMessage = "This garage is full!";
        when(garageRepository.findWithLockingById(garageId)).thenReturn(Optional.of(garage));
        when(fuelRepository.findById(fuelId)).thenReturn(Optional.of(fuel));
        when(carRepository.findCarsAmountByGarageId(garageId)).thenReturn(3);

        assertThatExceptionOfType(GarageIsFullWithCarsException.class)
                .isThrownBy(() -> service.save(carDto, garageId, fuelId))
                .withMessage(excMessage);

        verify(garageRepository).findWithLockingById(garageId);
        verify(fuelRepository).findById(fuelId);
        verify(carRepository).findCarsAmountByGarageId(garageId);
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    public void testSave_LpgInGarageNotAllowedCarIsLpg_ResultsInGarageNotHandleLpgException() {
        String excMessage = "In this garage lpg is not allowed!";
        when(garageRepository.findWithLockingById(garageId)).thenReturn(Optional.of(garage));
        when(fuelRepository.findById(fuelId)).thenReturn(Optional.of(fuel2));
        when(carRepository.findCarsAmountByGarageId(garageId)).thenReturn(2);

        assertThatExceptionOfType(GarageNotHandleLpgException.class)
                .isThrownBy(() -> service.save(car2Dto, garageId, fuelId))
                .withMessage(excMessage);

        verify(garageRepository).findWithLockingById(garageId);
        verify(fuelRepository).findById(fuelId);
        verify(carRepository).findCarsAmountByGarageId(garageId);
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    public void testSave_CarIsTooWideForGaragePlace_ResultsInGarageIsGaragePlaceIsTooNarrowException() {
        String excMessage = "Garage place is too narrow for your car!";
        when(garageRepository.findWithLockingById(garageId)).thenReturn(Optional.of(garage));
        when(fuelRepository.findById(fuelId)).thenReturn(Optional.of(fuel));
        when(carRepository.findCarsAmountByGarageId(garageId)).thenReturn(2);
        carDto.setWidth(2.51);

        assertThatExceptionOfType(GaragePlaceIsTooNarrowException.class)
                .isThrownBy(() -> service.save(carDto, garageId, fuelId))
                .withMessage(excMessage);

        verify(garageRepository).findWithLockingById(garageId);
        verify(fuelRepository).findById(fuelId);
        verify(carRepository).findCarsAmountByGarageId(garageId);
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    public void testSave_FuelNotFound_ResultsInGarageIsEntityNotFoundException() {
        String excMessage = "Fuel with id=1 not found";
        when(garageRepository.findWithLockingById(garageId)).thenReturn(Optional.of(garage));

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> service.save(carDto, garageId, fuelId))
                .withMessage(excMessage);

        verify(garageRepository).findWithLockingById(garageId);
        verify(fuelRepository).findById(fuelId);
        verifyNoInteractions(carRepository);
    }

    @Test
    public void testSave_GarageNotFound_ResultsInGarageIsEntityNotFoundException() {
        String excMessage = "Garage with id=1 not found";

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> service.save(carDto, garageId, fuelId))
                .withMessage(excMessage);

        verify(garageRepository).findWithLockingById(garageId);
        verifyNoInteractions(fuelRepository);
        verifyNoInteractions(carRepository);
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
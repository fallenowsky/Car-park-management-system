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
import pl.kurs.mmiaso.car.model.Car;
import pl.kurs.mmiaso.car.model.command.CreateCarCommand;
import pl.kurs.mmiaso.car.model.dto.CarDto;
import pl.kurs.mmiaso.fuel.FuelRepository;
import pl.kurs.mmiaso.fuel.model.Fuel;
import pl.kurs.mmiaso.garage.model.Garage;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private FuelRepository fuelRepository;
    @InjectMocks
    private CarService service;
    @Captor
    private ArgumentCaptor<Car> carArgumentCaptor;
    private Garage garage;
    private Car car;
    private Car car2;
    private Fuel fuel;
    private CarDto carDto;
    private CarDto car2Dto;
    private CreateCarCommand carCommand;
    private long garageId;

    @BeforeEach
    public void init() {
        fuel = Fuel.builder().name("Petrol").build();
        garage = Garage.builder().id(1L).capacity(3).isLpgAllowed(false).placeWidth(2.5).build();
        car = Car.builder().brand("BMW").width(2.3).price(BigDecimal.valueOf(299000.4)).fuel(fuel).garage(garage)
                .build();
        car2 = Car.builder().brand("LAMBORGHINI").width(2.9).price(BigDecimal.valueOf(4324212.4)).fuel(fuel).garage(garage)
                .build();
        carDto = CarDto.builder().garageName("fallenowsky").brand("BMW").width(2.3).price(BigDecimal.valueOf(299000.4)).fuelName("Petrol")
                .build();
        car2Dto = CarDto.builder().garageName("fallenowsky").brand("LAMBORGHINI").width(2.9).price(BigDecimal.valueOf(4324212.4)).fuelName("Petrol")
                .build();
        carCommand = CreateCarCommand.builder().brand("BMW").width(2.3).price(BigDecimal.valueOf(299000.4))
                .build();
        garageId = 1;
    }

    @Test
    public void testFindAllWithFuelJoin_AllCarsHasGarageAndAddress_ResultsInRepoCarDtoListReturnedAndMockMethodCall() {
        Address address = Address.builder()
                .name("fallenowsky")
                .street("Zlota")
                .zipCode("22-222")
                .city("Warszawa")
                .country("Poland")
                .build();
        car.setGarage(garage);
        garage.setAddress(address);
        car2.setGarage(garage);
        List<Car> cars = Arrays.asList(car, car2);
        List<CarDto> expectedDtos = Arrays.asList(carDto, car2Dto);
        when(carRepository.findAllWithFuelJoinAndGarageAddressJoin()).thenReturn(cars);

        List<CarDto> returned = service.findAllWithFuelJoin();

        assertEquals(expectedDtos, returned);
        verify(carRepository).findAllWithFuelJoinAndGarageAddressJoin();
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    public void testFindAllWithFuelJoin_FirstCarHasGarageSecondNot_ResultsInRepoCarDtoListReturnedAndMockMethodCall() {
        Address address = Address.builder()
                .name("fallenowsky")
                .street("Zlota")
                .zipCode("22-222")
                .city("Warszawa")
                .country("Poland")
                .build();
        car.setGarage(garage);
        garage.setAddress(address);
        car2.setGarage(null);
        car2Dto.setGarageName(null);
        List<Car> cars = Arrays.asList(car, car2);
        List<CarDto> expectedDtos = Arrays.asList(carDto, car2Dto);
        when(carRepository.findAllWithFuelJoinAndGarageAddressJoin()).thenReturn(cars);

        List<CarDto> returned = service.findAllWithFuelJoin();

        assertEquals(expectedDtos, returned);
        verify(carRepository).findAllWithFuelJoinAndGarageAddressJoin();
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    public void testFindAllWithFuelJoin_CarsHasNoGarage_ResultsInRepoCarDtoListReturnedAndMockMethodCall() {
        car.setGarage(null);
        car2.setGarage(null);
        carDto.setGarageName(null);
        car2Dto.setGarageName(null);
        List<Car> cars = Arrays.asList(car, car2);
        List<CarDto> expectedDtos = Arrays.asList(carDto, car2Dto);
        when(carRepository.findAllWithFuelJoinAndGarageAddressJoin()).thenReturn(cars);

        List<CarDto> returned = service.findAllWithFuelJoin();

        assertEquals(expectedDtos, returned);
        verify(carRepository).findAllWithFuelJoinAndGarageAddressJoin();
        verifyNoMoreInteractions(carRepository);
    }


    @Test
    public void testFindByIdWithFuelJoin_CarFound_ResultsInCarDtoReturnedAndMockMethodCall() {
        long carId = 1;
        when(carRepository.findByIdWithFuelJoin(carId)).thenReturn(Optional.of(car));
        car.setGarage(null);
        carDto.setGarageName(null);

        CarDto returned = service.findByIdWIthFuelJoin(carId);

        assertEquals(carDto, returned);
        verify(carRepository).findByIdWithFuelJoin(carId);
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    public void testFindByIdWithFuelJoin_CarNotFound_ResultsInEntityNotFoundException() {
        long carId = 1;
        String excMessage = "Car with id=1 not found";

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> service.findByIdWIthFuelJoin(carId))
                .withMessage(excMessage);

        verify(carRepository).findByIdWithFuelJoin(carId);
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    public void testSave_MethodInputsValid_ResultsInMockMethodCallAndCarCapture() {
        long fuelId = 1;
        when(fuelRepository.findById(fuelId)).thenReturn(Optional.of(fuel));

        service.save(carCommand, fuelId);

        verify(fuelRepository).findById(fuelId);
        verifyNoMoreInteractions(fuelRepository);
        verify(carRepository).save(carArgumentCaptor.capture());
        verify(carRepository).save(any(Car.class));
        Car carCaptured = carArgumentCaptor.getValue();
        assertEquals(carCommand.getPrice(), carCaptured.getPrice());
        assertEquals(carCommand.getWidth(), carCaptured.getWidth());
        assertEquals(carCommand.getBrand(), carCaptured.getBrand());
        assertEquals(fuel, carCaptured.getFuel());
    }

    @Test
    public void testFindByIdWithFuelJoin_FuelNotFound_ResultsInEntityNotFoundException() {
        long fuelId = 1;
        String excMessage = "Fuel with id=1 not found";

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> service.save(carCommand, fuelId))
                .withMessage(excMessage);

        verify(fuelRepository).findById(fuelId);
        verifyNoMoreInteractions(fuelRepository);
        verifyNoInteractions(carRepository);
    }

//
//    @Test
//    public void testSave_MethodInputsCorrect_ResultsInMockMethodCallAndCarCapture() {
//        when(garageRepository.findWithLockingById(garageId)).thenReturn(Optional.of(garage));
//        when(fuelRepository.findById(fuelId)).thenReturn(Optional.of(fuel));
//        when(garageRepository.findGarageCarsAmountById(garageId)).thenReturn(2);
//
//        service.save(carCommand, garageId, fuelId);
//
//        verify(garageRepository).findWithLockingById(garageId);
//        verify(fuelRepository).findById(fuelId);
//        verify(garageRepository).findGarageCarsAmountById(garageId);
//        verify(carRepository).save(carArgumentCaptor.capture());
//        Car carCaptured = carArgumentCaptor.getValue();
//        assertEquals(car.getBrand(), carCaptured.getBrand());
//        assertEquals(car.getGarage(), carCaptured.getGarage());
//        assertEquals(car.getFuel(), carCaptured.getFuel());
//        assertEquals(car.getPrice(), carCaptured.getPrice());
//        assertEquals(car.getWidth(), carCaptured.getWidth());
//    }
//
//    @Test
//    public void testSave_DbEntityLocked_ResultsInMaxOptimisticTriesExceededException() {
//        String excMessage = "Exceeded max tries to save a car!";
//        when(garageRepository.findWithLockingById(garageId)).thenReturn(Optional.of(garage));
//        when(fuelRepository.findById(fuelId)).thenReturn(Optional.of(fuel));
//        when(garageRepository.findGarageCarsAmountById(garageId)).thenReturn(2);
//        when(carRepository.save(any(Car.class))).thenThrow(OptimisticLockException.class);
//
//        assertThatExceptionOfType(MaxOptimisticTriesExceededException.class)
//                .isThrownBy(() -> service.save(carCommand, garageId, fuelId))
//                .withMessage(excMessage);
//
//        verify(garageRepository, atMost(3)).findWithLockingById(garageId);
//        verify(fuelRepository, atMost(3)).findById(fuelId);
//        verify(garageRepository, atMost(3)).findGarageCarsAmountById(garageId);
//        verify(carRepository, atMost(3)).save(any(Car.class));
//    }
//
//    @Test
//    public void testSave_GarageIsFull_ResultsInGarageIsFullWithCarsException() {
//        String excMessage = "This garage is full!";
//        when(garageRepository.findWithLockingById(garageId)).thenReturn(Optional.of(garage));
//        when(fuelRepository.findById(fuelId)).thenReturn(Optional.of(fuel));
//        when(garageRepository.findGarageCarsAmountById(garageId)).thenReturn(3);
//
//        assertThatExceptionOfType(GarageIsFullWithCarsException.class)
//                .isThrownBy(() -> service.save(carCommand, garageId, fuelId))
//                .withMessage(excMessage);
//
//        verify(garageRepository).findWithLockingById(garageId);
//        verify(fuelRepository).findById(fuelId);
//        verify(garageRepository).findGarageCarsAmountById(garageId);
//        verifyNoMoreInteractions(carRepository, garageRepository, fuelRepository);
//    }
//
//    @Test
//    public void testSave_LpgInGarageNotAllowedCarIsLpg_ResultsInGarageNotHandleLpgException() {
//        String excMessage = "In this garage lpg is not allowed!";
//        when(garageRepository.findWithLockingById(garageId)).thenReturn(Optional.of(garage));
//        when(fuelRepository.findById(fuelId)).thenReturn(Optional.of(fuel));
//        fuel.setName("Lpg");
//        when(garageRepository.findGarageCarsAmountById(garageId)).thenReturn(2);
//
//        assertThatExceptionOfType(GarageNotHandleLpgException.class)
//                .isThrownBy(() -> service.save(carCommand, garageId, fuelId))
//                .withMessage(excMessage);
//
//        verify(garageRepository).findWithLockingById(garageId);
//        verify(fuelRepository).findById(fuelId);
//        verify(garageRepository).findGarageCarsAmountById(garageId);
//        verifyNoMoreInteractions(carRepository, garageRepository, fuelRepository);
//    }
//
//    @Test
//    public void testSave_CarIsTooWideForGaragePlace_ResultsInGarageIsGaragePlaceIsTooNarrowException() {
//        String excMessage = "Garage place is too narrow for your car!";
//        when(garageRepository.findWithLockingById(garageId)).thenReturn(Optional.of(garage));
//        when(fuelRepository.findById(fuelId)).thenReturn(Optional.of(fuel));
//        when(garageRepository.findGarageCarsAmountById(garageId)).thenReturn(2);
//        carCommand.setWidth(2.51);
//
//        assertThatExceptionOfType(GaragePlaceIsTooNarrowException.class)
//                .isThrownBy(() -> service.save(carCommand, garageId, fuelId))
//                .withMessage(excMessage);
//
//        verify(garageRepository).findWithLockingById(garageId);
//        verify(fuelRepository).findById(fuelId);
//        verify(garageRepository).findGarageCarsAmountById(garageId);
//        verifyNoMoreInteractions(carRepository, garageRepository, fuelRepository);
//    }
//
//    @Test
//    public void testSave_FuelNotFound_ResultsInGarageIsEntityNotFoundException() {
//        String excMessage = "Fuel with id=1 not found";
//        when(garageRepository.findWithLockingById(garageId)).thenReturn(Optional.of(garage));
//
//        assertThatExceptionOfType(EntityNotFoundException.class)
//                .isThrownBy(() -> service.save(carCommand, garageId, fuelId))
//                .withMessage(excMessage);
//
//        verify(garageRepository).findWithLockingById(garageId);
//        verify(fuelRepository).findById(fuelId);
//        verifyNoMoreInteractions(garageRepository, fuelRepository);
//        verifyNoInteractions(carRepository);
//    }
//
//    @Test
//    public void testSave_GarageNotFound_ResultsInGarageIsEntityNotFoundException() {
//        String excMessage = "Garage with id=1 not found";
//
//        assertThatExceptionOfType(EntityNotFoundException.class)
//                .isThrownBy(() -> service.save(carCommand, garageId, fuelId))
//                .withMessage(excMessage);
//
//        verify(garageRepository).findWithLockingById(garageId);
//        verifyNoMoreInteractions(garageRepository);
//        verifyNoInteractions(fuelRepository);
//        verifyNoInteractions(carRepository);
//    }

    @Test
    public void testFindCarsByGarageId_GarageFound_ResultsInRepoCarsReturned() {
        List<Car> expectedCars = Arrays.asList(car, car2);
        List<CarDto> expectedCarsDto = Arrays.asList(carDto, car2Dto);
        car.setGarage(null);
        car2.setGarage(null);
        carDto.setGarageName(null);
        car2Dto.setGarageName(null);
        when(carRepository.findAllByGarageIdWithFuelJoin(garageId)).thenReturn(expectedCars);

        List<CarDto> returned = service.findCarsByGarageId(garageId);

        assertEquals(expectedCarsDto, returned);
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
//package pl.kurs.mmiaso.garage;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Captor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import pl.kurs.mmiaso.address.model.Address;
//import pl.kurs.mmiaso.car.CarRepository;
//import pl.kurs.mmiaso.car.model.Car;
//import pl.kurs.mmiaso.fuel.model.Fuel;
//import pl.kurs.mmiaso.garage.model.Garage;
//import pl.kurs.mmiaso.garage.model.dto.GarageDto;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class GarageServiceTest {
//    @Mock
//    private GarageRepository garageRepository;
//    @Mock
//    private CarRepository carRepository;
//    @InjectMocks
//    private GarageService service;
//    @Captor
//    private ArgumentCaptor<Garage> garageArgumentCaptor;
//    private Garage garage;
//    private Garage garage2;
//    private GarageDto garageDto;
//    private GarageDto garage2Dto;
//    private Fuel fuel;
//    private Car car;
//    private long garageId;
//
//    @BeforeEach
//    public void init() {
//        Address address = Address.builder().id(1L).name("fallenowsky").street("Zlota 44").zipCode("44-233")
//                .city("Warszawa").country("Poland").build();
//        Address address2 = Address.builder().id(2L).name("quarkowsky").street("Srebrna 44").zipCode("43-255")
//                .city("Warszawa").country("Poland").build();
//        garage = Garage.builder().id(1L).capacity(3).isLpgAllowed(true).address(address).build();
//        garage2 = Garage.builder().id(2L).capacity(5).isLpgAllowed(false).address(address2).build();
//        car = Car.builder().brand("BMW").width(2.7).price(BigDecimal.valueOf(29292.1)).build();
//        fuel = Fuel.builder().id(1L).name("Petrol").build();
//        garageId = 1L;
//    }
//
//    @Test
//    public void testFindAll_ResultsInRepositoryGaragesListReturnedAndMockMethodCall() {
//        List<Garage> garages = Arrays.asList(garage, garage2);
//        when(garageRepository.findALlWithAddressJoin()).thenReturn(garages);
//        when(carRepository.findMostCommonFuelByGarageId(garageId)).thenReturn(Optional.of(fuel));
//        when(carRepository.findGarageAverageCarsPriceByGarageId(garageId)).thenReturn(BigDecimal.valueOf(32321.22));
//        when(carRepository.findCarsAmountByGarageId(garageId)).thenReturn(2);
//        when(carRepository.findMostExpensiveCarByGarageId(garageId)).thenReturn(Optional.of(car));
//
//        List<GarageDto> returned = service.findAll();
//
//
//
//    }
//
//    @Test
//    public void testSave_findMostExpensiveCar_ResultsInMockMethodCallAndGarageCapture() {
//        when(garageRepository.findWithLockingById(garageId)).thenReturn(Optional.of(garage));
//
//
//
//        when(fuelRepository.findById(fuelId)).thenReturn(Optional.of(fuel));
//        when(carRepository.findCarsAmountByGarageId(garageId)).thenReturn(2);
//
//        service.save(carDto, garageId, fuelId);
//
//        verify(garageRepository).findWithLockingById(garageId);
//        verify(fuelRepository).findById(fuelId);
//        verify(carRepository).save(carArgumentCaptor.capture());
//        Car carCaptured = carArgumentCaptor.getValue();
//        assertEquals(car.getBrand(), carCaptured.getBrand());
//        assertEquals(car.getGarage(), carCaptured.getGarage());
//        assertEquals(car.getFuel(), carCaptured.getFuel());
//        assertEquals(car.getPrice(), carCaptured.getPrice());
//        assertEquals(car.getWidth(), carCaptured.getWidth());
//    }
//
//}
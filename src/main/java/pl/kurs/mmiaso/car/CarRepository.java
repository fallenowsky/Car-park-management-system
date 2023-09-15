package pl.kurs.mmiaso.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.mmiaso.car.model.Car;

import java.math.BigDecimal;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select C from Car as C where C.price = (select max(C2.price) from Car as C2)")
    Car findMostExpensive();

    @Query("select avg(C.price) from Car as C")
    BigDecimal findCarsAveragePrice();
}

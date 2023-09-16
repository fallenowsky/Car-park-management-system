package pl.kurs.mmiaso.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kurs.mmiaso.car.model.Car;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select C from Car as C " +
            "where C.garage.id = :id " +
            "order by C.price desc limit 1")
    Optional<Car> findMostExpensive(@Param("id") long garageId);

    @Query("select avg(C.price) from Car as C " +
            "where C.garage.id = :id")
    BigDecimal findGarageCarsAveragePrice(@Param("id") long garageId);

    @Query("select distinct C from Car as C " +
//            "left join fetch C.garage as G " +
            "left join fetch C.fuel " +
            "where C.garage.id = :id")
    List<Car> findAllByGarageIdWithFuelJoin(@Param("id") long garageId);
}

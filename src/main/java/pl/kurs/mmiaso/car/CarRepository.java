package pl.kurs.mmiaso.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kurs.mmiaso.car.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {

    //    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Car> findWithLockingById(Long carId);

    @Query("select C from Car as C " +
            "where C.garage.id = :id " +
            "order by C.price desc limit 1")
    Optional<Car> findMostExpensiveCarByGarageId(@Param("id") Long garageId);

    @Query("select distinct C from Car as C " +
            "join fetch C.fuel " +
            "where C.garage.id = :id")
    List<Car> findAllByGarageIdWithFuelJoin(@Param("id") Long garageId);

    @Query("select distinct C from Car as C " +
            "join fetch C.fuel " +
            "left join fetch C.garage as G " +
            "left join fetch G.address")
    List<Car> findAllWithFuelJoinAndGarageAddressJoin();

    @Query("select C from Car as C " +
            "join fetch C.fuel " +
            "where C.id = :id")
    Optional<Car> findByIdWithFuelJoin(@Param("id") long id);
}

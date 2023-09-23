package pl.kurs.mmiaso.garage;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kurs.mmiaso.garage.model.Garage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface GarageRepository extends JpaRepository<Garage, Long> {

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Garage> findWithLockingById(Long id);

    @Query("select distinct G from Garage as G " +
            "join fetch G.address ")
    List<Garage> findALlWithAddressJoin();

    @Query("select avg(C.price) from Garage as G " +
            "inner join G.cars as C " +
            "where G.id = :id")
    BigDecimal findGarageAverageCarsPriceById(@Param("id") Long id);

    @Query("select count(C.id) from Garage as G " +
            "inner join G.cars as C " +
            "where G.id = :id")
    int findGarageCarsAmountById(@Param("id") Long id);
}

package pl.kurs.mmiaso.fuel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kurs.mmiaso.fuel.model.Fuel;

import java.util.Optional;

public interface FuelRepository extends JpaRepository<Fuel, Long> {

    @Query("select F, count(F.id) as fuelCount " +
            "from Fuel as F " +
            "join F.cars as C " +
            "where C.garage.id = :id " +
            "group by F " +
            "order by fuelCount desc limit 1")
    Optional<Fuel> findMostUsedFuelByGarageId(@Param("id") Long garageId);
}

package pl.kurs.mmiaso.garage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kurs.mmiaso.garage.model.Garage;

import java.util.List;
import java.util.Optional;

public interface GarageRepository extends JpaRepository<Garage, Long> {

    @Query("select distinct G from Garage as G " +
            "left join fetch G.address " +
            "left join fetch G.cars")
    List<Garage> findALlWithAddressAndCarsJoin();

    @Query("select distinct G from Garage as G " +
            "left join fetch G.cars " +
            "where G.id = :id")
    Optional<Garage> findByIdWithCarsJoin(@Param("id") long garageId);

    @Query("select distinct G from Garage as G " +
            "left join fetch G.address " +
            "left join fetch G.cars " +
            "where G.id = :id")
    Optional<Garage> findByIdWithAddressAndCarsJoin(@Param("id") long garageId);

}

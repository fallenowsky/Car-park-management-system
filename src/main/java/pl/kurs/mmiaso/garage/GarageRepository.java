package pl.kurs.mmiaso.garage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.mmiaso.garage.model.Garage;

import java.util.List;

public interface GarageRepository extends JpaRepository<Garage, Long> {

    @Query("select distinct G from Garage as G " +
            "left join fetch G.address ")
    List<Garage> findALlWithAddressJoin();
}

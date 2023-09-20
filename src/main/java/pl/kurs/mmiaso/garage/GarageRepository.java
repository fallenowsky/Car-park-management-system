package pl.kurs.mmiaso.garage;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.mmiaso.garage.model.Garage;

import java.util.List;
import java.util.Optional;

public interface GarageRepository extends JpaRepository<Garage, Long> {

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Garage> findWithLockingById(Long id);

    @Query("select distinct G from Garage as G " +
            "left join fetch G.address ")
    List<Garage> findALlWithAddressJoin();
}

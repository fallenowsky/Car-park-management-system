package pl.kurs.mmiaso.garage;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.mmiaso.garage.model.Garage;

public interface GarageRepository extends JpaRepository<Garage, Long> {
}

package pl.kurs.mmiaso.fuel;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.mmiaso.fuel.model.Fuel;

public interface FuelRepository extends JpaRepository<Fuel, Long> {
}

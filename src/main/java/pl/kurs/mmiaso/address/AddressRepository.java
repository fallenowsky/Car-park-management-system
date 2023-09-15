package pl.kurs.mmiaso.address;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.mmiaso.address.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

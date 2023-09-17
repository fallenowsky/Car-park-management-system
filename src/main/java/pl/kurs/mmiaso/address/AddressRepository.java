package pl.kurs.mmiaso.address;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kurs.mmiaso.address.model.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("select A from Address as A " +
            "inner join A.garages as G " +
            "where G.id = :id")
    Optional<Address> findByGarageId(@Param("id") long garageId);
}

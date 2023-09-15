package pl.kurs.mmiaso.garage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.mmiaso.address.model.Address;
import pl.kurs.mmiaso.car.model.Car;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int places;
    private boolean isLpgAllowed;
    private double placeWidth;

    @ManyToOne
    private Address address;

    @OneToMany(mappedBy = "garage")
    private Set<Car> cars;
}

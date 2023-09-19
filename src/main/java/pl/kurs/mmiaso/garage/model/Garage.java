package pl.kurs.mmiaso.garage.model;

import jakarta.persistence.*;
import lombok.*;
import pl.kurs.mmiaso.address.model.Address;
import pl.kurs.mmiaso.car.model.Car;

import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="garages")

public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int capacity;
    private boolean isLpgAllowed;
    private double placeWidth;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Address address;

    @OneToMany(mappedBy = "garage")
    private Set<Car> cars;

}

package pl.kurs.mmiaso.fuel.model;

import jakarta.persistence.*;
import lombok.*;
import pl.kurs.mmiaso.car.model.Car;

import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fuel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "fuel")
    private Set<Car> cars;

}

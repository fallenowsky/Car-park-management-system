package pl.kurs.mmiaso.address.model;

import jakarta.persistence.*;
import lombok.*;
import pl.kurs.mmiaso.garage.model.Garage;

import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String street;
    private String zipCode;
    private String city;
    private String country;

    @OneToMany(mappedBy = "address")
    private Set<Garage> garages;

}

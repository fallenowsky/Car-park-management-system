package pl.kurs.mmiaso.car.model;

import jakarta.persistence.*;
import lombok.*;
import pl.kurs.mmiaso.fuel.model.Fuel;
import pl.kurs.mmiaso.garage.model.Garage;

import java.math.BigDecimal;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private double width;
    private BigDecimal price;

    /* relacje z Fuel unidirectional. zakładam, że paliwo nie musi wiedzieć w jakich autach jest uzywane
    *  dodajac auto to garazu mam info jakie ma paliwo. w razie potrzeby moge po aucie dojsc do paliw dla garazu */

    @ManyToOne(fetch = FetchType.LAZY)
    private Fuel fuel;

    @ManyToOne(fetch = FetchType.LAZY)
    private Garage garage;

}

package pl.kurs.mmiaso.car;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kurs.mmiaso.car.model.command.CreateCarCommand;
import pl.kurs.mmiaso.car.model.dto.CarDto;
import pl.kurs.mmiaso.fuel.FuelService;
import pl.kurs.mmiaso.garage.GarageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final FuelService fuelService;
    private final GarageService garageService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("cars", carService.findAllWithFuelJoin());
        return "car/showAll";
    }

    @GetMapping("/add")
    public String renderAddForm(Model model) {
        model.addAttribute("fuels", fuelService.findAll());
        return "car/create";
    }

    @GetMapping("/by-garage-id")
    public ResponseEntity<List<CarDto>> getAllByGarageId(@RequestParam("garageId") long garageId) {
        List<CarDto> cars = carService.findCarsByGarageId(garageId);
        return ResponseEntity.ok(cars);
    }

    @PostMapping("/add")
    public String save(@Valid @RequestBody CreateCarCommand command,
                       @RequestParam("fuelId") long fuelId) {
        carService.save(command, fuelId);
        return "redirect:/cars";
    }

    @GetMapping("/add-car")
    public String renderAddCarToGarageForm(@RequestParam("carId") long carId, Model model) {
        model.addAttribute("garages", garageService.findAll());
        model.addAttribute("car", carService.findByIdWIthFuelJoin(carId));
        return "car/assignCar";
    }


//    @PostMapping("/create")
//    public String save(
//            @Valid @RequestBody CreateCarCommand command,
//            @RequestParam("garageId") long garageId,
//            @RequestParam("fuelId") long fuelId) {
//
//        try {
//            carService.save(command, garageId, fuelId);
//            return "redirect:/garages";
//        } catch (MaxOptimisticTriesExceededException e) {
//            return "error/429";
//        }
//    }
}

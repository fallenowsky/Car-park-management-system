package pl.kurs.mmiaso.car;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        model.addAttribute("cars", carService.findAllWithFuelAndGarageAddressJoin());
        return "car/showAll";
    }

    @GetMapping("/add")
    public String renderAddForm(Model model) {
        model.addAttribute("fuels", fuelService.findAll());
        return "car/create";
    }

    @GetMapping("/by-garage-id")
    @ResponseBody
    public List<CarDto> findAllByGarageId(@RequestParam("garageId") long garageId) {
        return carService.findCarsByGarageId(garageId);
    }

    @GetMapping("/add-car")
    public String renderAddCarToGarageForm(@RequestParam("carId") long carId, Model model) {
        model.addAttribute("garages", garageService.findAllWithAddressJoin());
        model.addAttribute("car", carService.findByIdWIthFuelJoin(carId));
        return "car/assignCar";
    }

    @PostMapping("/add")
    public String save(@Valid @RequestBody CreateCarCommand command,
                       @RequestParam("fuelId") long fuelId) {
        carService.save(command, fuelId);
        return "redirect:/cars";
    }

}

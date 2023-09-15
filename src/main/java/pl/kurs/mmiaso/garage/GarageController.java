package pl.kurs.mmiaso.garage;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kurs.mmiaso.car.CarService;
import pl.kurs.mmiaso.fuel.FuelService;
import pl.kurs.mmiaso.garage.model.dto.GarageDto;

@Controller
@RequiredArgsConstructor
@RequestMapping("/garage")

public class GarageController {
    private final GarageService garageService;
    private final CarService carService;
    private final FuelService fuelService;

    @GetMapping
    public String renderAllGarages(Model model) {
        model.addAttribute("garages", garageService.findAll());
        model.addAttribute("mostExpensive", carService.findMostExpensive());
        model.addAttribute("averagePrice", carService.findCarsAveragePrice());
//        model.addAttribute("mostFuel", fuelService.findMostFuelTypeInGarage());
        return "garage/showAll";
    }

    @GetMapping("/addForm")
    public String renderAddGarageForm() {
        return "garage/create";
    }

    @PostMapping("/create")
    public String create(@Valid GarageDto garageDto) {
        garageService.save(garageDto);
        return "redirect:/garage";
    }

}

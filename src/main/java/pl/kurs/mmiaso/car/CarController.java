package pl.kurs.mmiaso.car;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kurs.mmiaso.car.model.dto.CarDto;
import pl.kurs.mmiaso.fuel.FuelService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/car")

public class CarController {
    private final CarService carService;
    private final FuelService fuelService;

    @GetMapping("/addForm")
    public String renderAddCarForm(@RequestParam("garageId") long id, Model model) {
        model.addAttribute("garageId", id);
        model.addAttribute("fuels", fuelService.findAll());
        return "car/create";
    }

    @PostMapping("/create")
    public String create(
            CarDto carDto,
            @RequestParam("garageId") long garageId,
            @RequestParam("fuelId") long fuelId) {
        carService.save(carDto, garageId, fuelId);
        return "redirect:/cars";
    }
}

package pl.kurs.mmiaso.car;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kurs.mmiaso.car.model.dto.CarDto;
import pl.kurs.mmiaso.fuel.FuelService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cars")

public class CarController {
    private final CarService carService;
    private final FuelService fuelService;

    @GetMapping("/addForm")
    public String renderAddForm(@RequestParam("garageId") long id, Model model) {
        model.addAttribute("garageId", id);
        model.addAttribute("fuels", fuelService.findAll());
        return "car/create";
    }

    @GetMapping("/by-garage-id")
    @ResponseBody
    public ResponseEntity<List<CarDto>> getAllByGarageId(@RequestParam("garageId") long garageId) {
        List<CarDto> cars = carService.findCarsByGarageId(garageId);
        return ResponseEntity.ok(cars);
    }

    @PostMapping("/create")
    public String create(
            @Valid @RequestBody CarDto carDto,
            @RequestParam("garageId") long garageId,
            @RequestParam("fuelId") long fuelId) {

        carService.save(carDto, garageId, fuelId);
        return "redirect:/garages";
    }
}

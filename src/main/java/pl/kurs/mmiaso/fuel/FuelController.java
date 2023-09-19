package pl.kurs.mmiaso.fuel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kurs.mmiaso.fuel.exceptions.ThisFuelAlreadyExists;
import pl.kurs.mmiaso.fuel.model.dto.FuelDto;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fuels")

public class FuelController {
    private final FuelService fuelService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("fuels", fuelService.findAll());
        return "fuel/showAll";
    }

    @GetMapping("/create")
    public String displayCreateForm() {
        return "fuel/create";
    }

    @PostMapping("/create")
    public String create(@Valid FuelDto fuelDto, Model model) {
        try {
            fuelService.save(fuelDto);
            return "redirect:/fuels";
        } catch (ThisFuelAlreadyExists e) {
            model.addAttribute("errMessage", e.getMessage());
        }
        return "fuel/create";
    }

}

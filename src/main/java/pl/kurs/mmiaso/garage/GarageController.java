package pl.kurs.mmiaso.garage;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kurs.mmiaso.address.model.command.CreateAddressCommand;
import pl.kurs.mmiaso.garage.model.command.CreateGarageCommand;

@Controller
@RequiredArgsConstructor
@RequestMapping("/garages")
public class GarageController {
    private final GarageService garageService;

    @GetMapping
    public String renderAllGarages(Model model) {
        model.addAttribute("garages", garageService.findAll());
        return "garage/showAll";
    }

    @GetMapping("/addForm")
    public String renderAddGarageForm() {
        return "garage/create";
    }

    @PostMapping("/create")
    public String create(@Valid CreateGarageCommand garageCommand,
                         @Valid CreateAddressCommand addressCommand) {
        garageService.save(garageCommand, addressCommand);
        return "redirect:/garages";
    }

}

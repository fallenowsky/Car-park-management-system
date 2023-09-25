package pl.kurs.mmiaso.garage;

import jakarta.persistence.OptimisticLockException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kurs.mmiaso.address.model.command.CreateAddressCommand;
import pl.kurs.mmiaso.garage.model.command.CreateGarageCommand;

@Controller
@RequiredArgsConstructor
@RequestMapping("/garages")
public class GarageController {
    private final GarageService garageService;

    @GetMapping
    public String renderAllGarages(Model model) {
        model.addAttribute("garages", garageService.findAllWithDetails());
        return "garage/showAll";
    }

    @GetMapping("/addForm")
    public String renderAddGarageForm() {
        return "garage/create";
    }

    @PostMapping("/add")
    public String save(@Valid CreateGarageCommand garageCommand,
                       @Valid CreateAddressCommand addressCommand) {
        garageService.save(garageCommand, addressCommand);
        return "redirect:/garages";
    }

    @PostMapping("/add-car")
    public String assignCar(@RequestParam("carId") long carId,
                            @RequestParam("garageId") long garageId) {
        try {
            garageService.assignCar(garageId, carId);
            return "redirect:/garages";
        } catch (OptimisticLockException e) {
            return "error/429";
        }
    }

}

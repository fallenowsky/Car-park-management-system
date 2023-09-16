package pl.kurs.mmiaso.garage;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kurs.mmiaso.address.model.dto.AddressDto;
import pl.kurs.mmiaso.car.CarService;
import pl.kurs.mmiaso.garage.model.dto.GarageDto;

@Controller
@RequiredArgsConstructor
@RequestMapping("/garages")

public class GarageController {
    private final GarageService garageService;
    private final CarService carService;

    @GetMapping
    public String renderAllGarages(Model model) {
        model.addAttribute("garages", garageService.findAll());
        return "garage/showAll";
    }

    @GetMapping("/cars")
    public String renderGarageCars(@RequestParam("garageId") long garageId, Model model) {
        model.addAttribute("garage", garageService.findById(garageId));
        model.addAttribute("cars", carService.findCarsByGarageId(garageId));
        return "garage/garageCars";
    }


    @GetMapping("/addForm")
    public String renderAddGarageForm() {
        return "garage/create";
    }

    @PostMapping("/create") // TODO: 16.09.2023 zapisywanie adresu garazu jakos poprawic usunac cascade isetowac adres
    public String create(@Valid GarageDto garageDto, @Valid AddressDto addressDto) {
        garageService.save(garageDto, addressDto);
        return "redirect:/garages";
    }

}

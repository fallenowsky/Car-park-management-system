package pl.kurs.mmiaso.address;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.kurs.mmiaso.address.model.dto.AddressDto;

@Controller
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/get")
    @ResponseBody
    public AddressDto findByGarageId(@RequestParam("garageId") long garageId) {
        return addressService.findByGarageId(garageId);
    }

}

package pl.kurs.mmiaso.address;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.kurs.mmiaso.address.model.dto.AddressDto;

@Controller
@RequiredArgsConstructor
@RequestMapping("/address")

public class AddressController {
    private final AddressService addressService;

    @GetMapping("/get")
    @ResponseBody
    public ResponseEntity<AddressDto> getByGarageId(@RequestParam("garageId") long garageId) {
        AddressDto address = addressService.findByGarageId(garageId);
        return ResponseEntity.ok(address);
    }
}

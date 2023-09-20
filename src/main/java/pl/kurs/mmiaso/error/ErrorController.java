package pl.kurs.mmiaso.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("/*")
    public String handleNotFound() {
        return "429";
    }
}

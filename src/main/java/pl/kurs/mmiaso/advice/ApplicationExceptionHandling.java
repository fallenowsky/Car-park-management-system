package pl.kurs.mmiaso.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.kurs.mmiaso.car.exceptions.GarageIsFullWithCarsException;
import pl.kurs.mmiaso.car.exceptions.GarageNotHandleLpgException;
import pl.kurs.mmiaso.car.exceptions.GaragePlaceIsTooNarrowException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApplicationExceptionHandling {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GarageIsFullWithCarsException.class)
    public Map<String, String> handleGaragePlaceIsTooNarrowException(GarageIsFullWithCarsException e) {
        Map<String, String> errMap = new HashMap<>();
        errMap.put("message", e.getMessage());
        return errMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GarageNotHandleLpgException.class)
    public Map<String, String> handleGaragePlaceIsTooNarrowException(GarageNotHandleLpgException e) {
        Map<String, String> errMap = new HashMap<>();
        errMap.put("message", e.getMessage());
        return errMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GaragePlaceIsTooNarrowException.class)
    public Map<String, String> handleGaragePlaceIsTooNarrowException(GaragePlaceIsTooNarrowException e) {
        Map<String, String> errMap = new HashMap<>();
        errMap.put("message", e.getMessage());
        return errMap;
    }

}

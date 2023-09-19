package pl.kurs.mmiaso.car.exceptions;

public class GarageIsFullWithCarsException extends RuntimeException {
    public GarageIsFullWithCarsException() {
    }

    public GarageIsFullWithCarsException(String message) {
        super(message);
    }
}

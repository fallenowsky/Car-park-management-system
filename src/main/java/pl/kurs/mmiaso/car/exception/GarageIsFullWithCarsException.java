package pl.kurs.mmiaso.car.exception;

public class GarageIsFullWithCarsException extends RuntimeException {
    public GarageIsFullWithCarsException() {
    }

    public GarageIsFullWithCarsException(String message) {
        super(message);
    }
}

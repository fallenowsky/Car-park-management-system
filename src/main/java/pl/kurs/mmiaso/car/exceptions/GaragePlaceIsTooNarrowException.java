package pl.kurs.mmiaso.car.exceptions;

public class GaragePlaceIsTooNarrowException extends RuntimeException {
    public GaragePlaceIsTooNarrowException() {
    }

    public GaragePlaceIsTooNarrowException(String message) {
        super(message);
    }
}

package pl.kurs.mmiaso.car.exception;

public class GaragePlaceIsTooNarrowException extends RuntimeException {
    public GaragePlaceIsTooNarrowException() {
    }

    public GaragePlaceIsTooNarrowException(String message) {
        super(message);
    }
}

package pl.kurs.mmiaso.fuel.exceptions;

public class ThisFuelAlreadyExists extends RuntimeException {
    public ThisFuelAlreadyExists() {
    }

    public ThisFuelAlreadyExists(String message) {
        super(message);
    }
}

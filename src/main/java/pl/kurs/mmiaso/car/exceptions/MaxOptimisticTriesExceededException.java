package pl.kurs.mmiaso.car.exceptions;

public class MaxOptimisticTriesExceededException extends RuntimeException {
    public MaxOptimisticTriesExceededException() {
    }

    public MaxOptimisticTriesExceededException(String message) {
        super(message);
    }
}

package org.example.lb3.exception;

public class CarAlreadyExistsException extends RuntimeException {
    public CarAlreadyExistsException(String carNumber) {
        super("Car with number " + carNumber + " already exists.");
    }
}


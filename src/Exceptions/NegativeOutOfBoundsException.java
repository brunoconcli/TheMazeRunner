package Exceptions;

public class NegativeOutOfBoundsException extends Exception {
    
    public NegativeOutOfBoundsException() {
        super("Index passed is out of bounds");
    }
}

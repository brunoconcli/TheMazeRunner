package Exceptions;

public class IndexOutOfBoundsException extends Exception {
    public IndexOutOfBoundsException(int length) {
        super("Index passed is out of bounds for: " + length);
    }
}

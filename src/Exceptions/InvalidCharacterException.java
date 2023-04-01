package Exceptions;

public class InvalidCharacterException extends Exception {
    public InvalidCharacterException() {
        super("Character passed is invalid for either '#', 'S', 'E' or ' '");
    }
}

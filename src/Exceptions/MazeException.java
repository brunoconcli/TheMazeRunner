package Exceptions;

public class MazeException extends Exception {
    public MazeException(MazeTypeError e) {
        super(e.toString());
    }
}


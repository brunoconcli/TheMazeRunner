package Exceptions;

public enum MazeTypeError {
    ENTRYEXCESS("More than one entry ('E') has been found in the maze"),
    EXITEXCESS("More than one exit ('S') has been found in the maze"),

    CORNERENTRY("Maze entry must not be in a corner"),
    CORNEREXIT("Maze exit must not be in a corner"),

    NOENTRY("Maze entry hasn't been found"),
    NOEXIT("Maze way out hasn't been found"),

    INNERENTRY("The maze entry must be in one of the borders"),
    INNEREXIT("The maze exit must be in one of the borders"),

    NEGATIVEDIMENSION("Maze dimensions must not be negative"),
    TOOSMALLDIMENSION("Maze dimensions are too small"),

    INDEXOFFBOUNDS("Index is out of maze bounds when trying to insert or preview a char "),

    BORDERHOLE("There's a hole in the border!"),

    LAST("");
    private final String error;

    MazeTypeError(String error) {
        this.error = error;
    }

    public String toString() {
        return this.error;
    }
}

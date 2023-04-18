import Exceptions.InvalidCharacterException;
import Exceptions.MazeException;
import Exceptions.MazeTypeError;

import java.util.Arrays;

import Colors.Colors;

public class Maze implements Cloneable {
    private char[][] matrix;
    private final int height;
    private final int width;
    private Coordinate entry;
    private Coordinate exit;
    private Stack<Coordinate> path;
    private Queue<Coordinate> queue;
    private Stack<Queue<Coordinate>> possibilities;

    public Maze(int height, int width) throws Exception {
        if (height <= 0 || width <= 0)
            throw new MazeException(MazeTypeError.NEGATIVEDIMENSION);
        if (height < 3 || width < 3)
            throw new MazeException(MazeTypeError.TOOSMALLDIMENSION);
        this.height = height;
        this.width = width;
        this.matrix = new char[this.height][this.width];
        this.path = new Stack<Coordinate>(this.getHeight() * this.getWidth());
        this.queue = new Queue<Coordinate>(3);
        this.possibilities = new Stack<Queue<Coordinate>>((this.getHeight() * this.getWidth()));
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public Coordinate getEntry() {
        return this.entry;
    }

    public Coordinate getExit() {
        return this.exit;
    }
    
    public Coordinate getCurrent() throws Exception {
        return (Coordinate) this.path.peek();
    }

    public char getPosition(int row, int column) throws Exception {
        if (row < 0 || column < 0)
            throw new MazeException(MazeTypeError.NEGATIVEDIMENSION);
        if(row >= this.getHeight())
            throw new MazeException(MazeTypeError.INDEXOFFBOUNDS);
        if (column >= this.getWidth())
            throw new MazeException(MazeTypeError.INDEXOFFBOUNDS);
        return this.matrix[row][column];
    }

    public void setChar(int row, int column, char character) throws Exception{
        if (row < 0 || column < 0)
            throw new MazeException(MazeTypeError.NEGATIVEDIMENSION);
        if (row >= this.getHeight())
            throw new MazeException(MazeTypeError.INDEXOFFBOUNDS);
        if (column >= this.getWidth()) 
            throw new MazeException(MazeTypeError.INDEXOFFBOUNDS);
        if ("#SE *.".indexOf(character) == -1)
            throw new InvalidCharacterException(); 
        
        this.matrix[row][column] = character;
    } 

    public void start() throws Exception {
        this.verifyEntry();
        this.verifyExit();
        this.verifyBounds();
    }

    private Coordinate verifyEntry() throws Exception {
        int[] rowList = {0, this.getHeight()-1};
        int entryCount = 0;

        entry = new Coordinate(0, 0);

        if (this.matrix[0][0] == 'E' ||
            this.matrix[0][this.getWidth()-1] == 'E' ||
            this.matrix[this.getHeight()-1][0] == 'E' ||
            this.matrix[this.getHeight()-1][this.getWidth()-1] == 'E')
                throw new MazeException(MazeTypeError.CORNERENTRY);

        for (int i = 0; i < 2; i++) {
            for (int col = 1; col < this.getWidth()-1; col++) {
                if (this.matrix[rowList[i]][col] == 'E') {
                    if (entryCount >= 1)
                        throw new MazeException(MazeTypeError.ENTRYEXCESS);
                    entry = new Coordinate(rowList[i], col);
                    entryCount ++;
                } 
            }
        }

        int[] colList = {0, this.getWidth()-1};
        for (int s = 0; s < 2; s++) {
            for (int row = 1; row < this.getHeight()-1; row++) {
                if (matrix[row][colList[s]] == 'E') {
                    if (entryCount >= 1)
                        throw new MazeException(MazeTypeError.ENTRYEXCESS);
                    entry = new Coordinate(row, colList[s]);
                    entryCount++;
                }
            }
        }

        for (int row = 1; row < this.getHeight()-1; row++)
            for (int col = 1; col < this.getWidth()-1; col++) 
                if (this.matrix[row][col] == 'E')
                    throw new MazeException(MazeTypeError.INNERENTRY);
    
        if (entryCount == 0) 
            throw new MazeException(MazeTypeError.NOENTRY);
        
        this.path.push(entry);
        return entry;
    }

    private Coordinate verifyExit() throws Exception {
        int[] rowList = {0, this.getHeight()-1};
        int exitCount = 0;

        exit = new Coordinate(0, 0);

        if (this.matrix[0][0] == 'S' ||
            this.matrix[0][this.getWidth()-1] == 'S' ||
            this.matrix[this.getHeight()-1][0] == 'S' ||
            this.matrix[this.getHeight()-1][this.getWidth()-1] == 'S')
                throw new MazeException(MazeTypeError.CORNEREXIT);

        for (int i = 0; i < 2; i++) {
            for (int col = 1; col < this.getWidth()-1; col++) {
                if (this.matrix[rowList[i]][col] == 'S') {
                    if (exitCount >= 1)
                        throw new MazeException(MazeTypeError.EXITEXCESS);
                    exit = new Coordinate(rowList[i], col);
                    exitCount ++;
                } 
            }
        }

        int[] colList = {0, this.getWidth()-1};
        for (int s = 0; s < 2; s++) {
            for (int row = 1; row < this.getHeight()-1; row++) {
                if (matrix[row][colList[s]] == 'S') {
                    if (exitCount >= 1)
                        throw new MazeException(MazeTypeError.EXITEXCESS);
                    exit = new Coordinate(row, colList[s]);
                    exitCount++;
                }
            }
        }

        for (int row = 1; row < this.getHeight()-1; row++)
            for (int col = 1; col < this.getWidth()-1; col++) 
                if (this.matrix[row][col] == 'S')
                    throw new MazeException(MazeTypeError.INNEREXIT);

        if (exitCount == 0) {
            throw new MazeException(MazeTypeError.NOEXIT);
        }

        return exit;
    }
    
    private boolean verifyBounds() throws Exception {
         
        for (int col = 1; col < this.getWidth()-1; col++) {
            if (this.matrix[0][col] == ' ' || this.matrix[0][col] == '*') throw new MazeException(MazeTypeError.BORDERHOLE);
            if (this.matrix[this.getHeight() - 1][col] == ' ' || this.matrix[this.getHeight() - 1][col] == '*') throw new MazeException(MazeTypeError.BORDERHOLE);
        }
        
        for (int row = 1; row < this.getHeight()-1; row++) {
            if (this.matrix[row][0] == ' ' || this.matrix[row][0] == '*') throw new MazeException(MazeTypeError.BORDERHOLE);
            if (this.matrix[row][this.getWidth() - 1] == ' ' || this.matrix[row][this.getWidth() - 1] == '*') throw new MazeException(MazeTypeError.BORDERHOLE);
        }
        return true;
    }

    public void getAdjacentPositions(Coordinate current) throws Exception {
        Coordinate position;
        int[] rowList = {-1, +1, 0, 0};
        int[] colList = {0, 0, -1, +1};
        this.queue = new Queue<Coordinate>(3);

        for (int i = 0; i < rowList.length; i++) {
            int row = current.getRow() + rowList[i];
            int col = current.getCol() + colList[i];
            if ((row >= 0 && col >= 0) && (row <= this.getHeight() -1 && col <= this.getWidth() -1) ) {
                position = new Coordinate(row, col);
                if ((this.matrix[position.getRow()][position.getCol()] == ' ' || this.matrix[position.getRow()][position.getCol()] == 'S') && !path.peek().equals(position) && this.matrix[position.getRow()][position.getCol()] != '*') {
                    if (this.matrix[position.getRow()][position.getCol()] == 'S') {
                        this.queue = new Queue<Coordinate>(3);
                        this.queue.push(position);
                        break;
                    }
                    this.queue.push(position);
                }
            }
        }
    }

    public void move() throws Exception {
        this.getAdjacentPositions(this.getCurrent());
        if (!this.queue.isEmpty()) {
            this.path.push(this.queue.peek());
            this.queue.pop();
            
            this.possibilities.push(this.queue);
            if (this.getPosition(((Coordinate) this.path.peek()).getRow(), ((Coordinate)this.path.peek()).getCol()) != 'S') {
                this.setChar(((Coordinate) this.path.peek()).getRow(), ((Coordinate)this.path.peek()).getCol() , '*');
            }
        }
        else this.rollback();
    }

    @SuppressWarnings("unchecked")
    public void rollback() throws Exception {
        this.setChar(((Coordinate) this.path.peek()).getRow(), ((Coordinate)this.path.peek()).getCol(), '.');
        this.path.pop();
        this.queue = (Queue<Coordinate>) this.possibilities.peek();
        this.possibilities.pop();
    }
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    @Override
    public int hashCode() {
        try {
            int hash = 2;
            hash = 3 * hash + Integer.valueOf(this.height).hashCode();
            hash = 5 * hash + Integer.valueOf(this.width).hashCode();
            hash = 7 * hash + Arrays.deepHashCode(this.matrix);
            hash = 11 * hash + new Stack<Coordinate>(this.getHeight() * this.getWidth()).hashCode();
            hash = 17 * hash + new Queue<Coordinate>(3).hashCode();
            hash = 19 * hash + new Stack<Queue<Coordinate>>(this.getHeight() * this.getWidth()).hashCode();
            hash = 23 * hash + new Coordinate(this.exit.getRow(), this.exit.getCol()).hashCode();
            hash = 29 * hash + new Coordinate(this.entry.getRow(), this.entry.getCol()).hashCode();
    
            if (hash < 0) hash = -hash;
            return hash;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    @Override
    public String toString() {
        String line = "";
        for (int r = 0; r < this.height; r++) {
            for (int c = 0; c < this.width; c++) {
                if (this.matrix[r][c] == '*')
                    line += Colors.YELLOW + "" + this.matrix[r][c];
                else 
                    line += Colors.RESET + "" + this.matrix[r][c];
            }
            line += "\n";
        }
        return line.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Maze mazeCopy = (Maze) super.clone();
        try {
            mazeCopy.matrix = new char[this.getHeight()][this.getWidth()];
            for (int i = 0; i < this.getHeight(); i++)
                for (int j = 0; j < this.getWidth(); j++)
                    mazeCopy.matrix[i][j] = this.matrix[i][j];

            mazeCopy.entry = new Coordinate(this.entry.getRow(), this.entry.getCol());
            mazeCopy.exit = new Coordinate(this.exit.getRow(), this.exit.getCol());
            mazeCopy.path = new Stack<Coordinate>(this.getHeight() * this.getWidth());
            mazeCopy.queue = new Queue<Coordinate>(3);
            mazeCopy.possibilities = new Stack<Queue<Coordinate>>(this.getHeight() * this.getWidth());


        }
        catch (Exception e) {}
        
        return mazeCopy;
    }
}

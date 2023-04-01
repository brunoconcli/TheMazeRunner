import Exceptions.InvalidCharacterException;
import Exceptions.MazeException;
import Exceptions.MazeTypeError;
import Colors.Colors;

public class Maze {
    private char[][] matrix;
    private final int height;
    private final int width;
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

    // protected Maze(char[][] labirinth) throws Exception {
    //     if (labirinth == null) 
    //         throw new Exception("Maze passed cannot be null, silly");
    //     this.matrix = labirinth;
    // }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
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
    public Coordinate verifyEntry() throws Exception {
        int[] rowList = {0, this.getHeight()-1};
        int entryCount = 0;

        Coordinate coord = new Coordinate(0, 0);

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
                    coord = new Coordinate(rowList[i], col);
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
                    coord = new Coordinate(row, colList[s]);
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
        
        this.path.push(coord);
        return coord;
    }
    // change to private
    // decrease the size using loops

    public Coordinate verifyExit() throws Exception {
        int[] rowList = {0, this.getHeight()-1};
        int exitCount = 0;

        Coordinate coord = new Coordinate(0, 0);

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
                    coord = new Coordinate(rowList[i], col);
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
                    coord = new Coordinate(row, colList[s]);
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

        return coord;
    }
    // change to private
    public boolean verifyBounds() throws Exception {
         
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

    public void rollback() throws Exception {
        this.setChar(((Coordinate) this.path.peek()).getRow(), ((Coordinate)this.path.peek()).getCol(), '.');
        this.path.pop();
        this.queue = this.possibilities.peek();
        this.possibilities.pop();
    }
    
    public String pathToString() {
        return this.path.toString();
    }
    public String queueToString() {
        return this.queue.toString();
    }
    public String possibilitiesToString() {
        return this.possibilities.toString();
    }

    public Coordinate getCurrent() throws Exception {
        return (Coordinate) this.path.peek();
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    @Override
    public int hashCode() {
        int hash = 2;
        hash = 3 * hash + Integer.valueOf(this.height).hashCode();
        hash = 5 * hash + Integer.valueOf(this.width).hashCode();
        hash = 7 * hash + (this.matrix).hashCode();
        hash = 11 * hash + (this.path).hashCode();
        hash = 17 * hash + (this.queue).hashCode();
        hash = 19 * hash + (this.possibilities).hashCode();

        if (hash < 0) hash = -hash;
        return hash;
    }
    @Override
    public String toString() {
        // StringBuilder line = new StringBuilder();
        String line = "";
        for (int r = 0; r < this.height; r++) {
            for (int c = 0; c < this.width; c++) {
                if (this.matrix[r][c] == '*')
                    line += Colors.YELLOW + "" + this.matrix[r][c];
                else 
                    line += Colors.RESET + "" + this.matrix[r][c];
            }
                // line.append(this.matrix[r][c] + "");
            // line.append("\n");
            line += "\n";
        }
        return line.toString();
    }
    // @Override
    // public Object clone() {
    //     try {
    //         return Maze 
    //     }
    // }
}

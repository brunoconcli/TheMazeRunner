public class Coordinate {
    protected int row, col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public int getRow() {
        return this.row;
    }
    public int getCol() {
        return this.col;
    }

    @Override
    public String toString() {
        return "( " + this.getRow() + ", " + this.getCol() + " )"; 
    }

    @Override
    public int hashCode() {
        int hash = 2;
        hash = 3 * hash + Integer.valueOf(this.row).hashCode();
        hash = 5 * hash + Integer.valueOf(this.col).hashCode();
        if (hash < 0) hash = -hash;
        return hash;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;

        if (this.getClass() != obj.getClass()) return false;

        Coordinate c = (Coordinate)obj;
        if (this.row != c.row) return false;
        if (this.col != c.col) return false;
        
        return true;
    }
}

import java.io.*;
import Exceptions.NegativeOutOfBoundsException;

public class Reader {
    private BufferedReader bufferedReader;
    private char[] line;

    public Reader(File file) throws Exception {
        this.bufferedReader = new BufferedReader(new FileReader(file));
    }

    public char[] readLine() throws Exception {
        this.line = this.bufferedReader.readLine().toCharArray();
        return this.line;
    }

    public char getCharInCol(int col) throws Exception {
        if (col < 0)
            throw new NegativeOutOfBoundsException();
        if (col >= this.line.length)
            throw new IndexOutOfBoundsException(this.line.length);

        return this.line[col];
    }

    public int getBounds() throws Exception {
        return Integer.parseInt(bufferedReader.readLine());
    }

    public int hashCode() {
        int hash = 2;
        hash = 3 * hash + new BufferedReader(this.bufferedReader).hashCode();
        for (int i = 0; i <this.line.length; i++) 
            hash = 5 * hash + Character.valueOf(this.line[i]).hashCode();

        if (hash < 0) hash = -hash;
        return hash;
    }

    public String toString() {
        StringBuilder message = new StringBuilder("");
        for (int i = 0; i< this.line.length; i++) {
            message.append(this.line[i]);
        }

        return message.toString();
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;

        if (this.getClass() != obj.getClass()) return false;

        Reader c = (Reader)obj;
        if (this.line != c.line) return false;
        
        return true;
    }
}

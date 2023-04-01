import java.io.*;
import java.util.concurrent.TimeUnit;
import Colors.Colors;

public class App {
    public static void main(String[] args) {
        try {
            //File file = new File("/Users/u22303/Desktop/Labirintos corretos para teste/Teste1.txt");
            //File working = new File("Y:\\Labirintos corretos para teste\\Teste1.txt");
            String[] mazeTests = {"Teste1", "Teste2", "Teste3", "Teste4", "Teste5", "Teste6"};
            Maze.clear();

            for (int m = 0; m < mazeTests.length; m++) {

                File file = new File("src/Mazes/" + mazeTests[m] + ".txt");
                Reader myReader = new Reader(file);
                Maze myMaze = new Maze(myReader.getBounds(), myReader.getBounds());
    
                for (int row = 0; row<myMaze.getHeight(); row++) {
                    myReader.readLine();
                    for (int col = 0; col<myMaze.getWidth(); col++) 
                        myMaze.setChar(row, col, myReader.getCharInCol(col));
                        
                }
                // the big three:
                myMaze.verifyEntry();
                myMaze.verifyExit();
                myMaze.verifyBounds();
    
                int counter = 0;
                long startTime = System.currentTimeMillis();
                String[] map = new String[myMaze.getHeight()*myMaze.getWidth()];
                for (;;) {
                    map[counter] = myMaze.pathToString();
                    
                    if (!myMaze.getCurrent().equals(myMaze.verifyExit())) {
                        myMaze.move();
                    }
                    else break;
                    
                    counter ++;
                }
                long endTime = System.currentTimeMillis();
                System.out.println("\n\n" + mazeTests[m].toUpperCase() + " To String():\n"+ myMaze.toString());
                System.out.println(Colors.RESET+"Congrats you've made it!\nTotal steps: " + counter + "\nTotal ms: " + (endTime - startTime));
                // System.out.println("Path used: ");
                // for (int i = 0; i<map.length; i++)
                //     System.out.print(map[i] + " ");
            }

        } // get the actual amount in millis, get colour in stars (asterísicos) and check if there's no way out in the maze (returned to the begining)

        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

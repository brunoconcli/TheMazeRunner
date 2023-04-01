import java.io.*;

public class AlternativeTests {
    public void testing() {
        try {

            File file = new File("src/Mazes/Teste1.txt");
            Reader myReader = new Reader(file);
            Maze myMaze = new Maze(myReader.getBounds(), myReader.getBounds());

            System.out.println("Entry: " + myMaze.verifyEntry());
            System.out.println("Exit: " + myMaze.verifyExit()); // testar o que acontece se a entrada ou saída estiver dentro do lab!!
            System.out.println("Borders are correct: " + myMaze.verifyBounds());
            
            // MOVING
            System.out.println("Verifying if you can move: " );
            // myMaze.getAdjacentPositions(new Coordinate(1, 1));
            myMaze.getAdjacentPositions(myMaze.getCurrent());
            myMaze.move();
            System.out.println("Queue: " + myMaze.queueToString());
            System.out.println("Possibilities: " + myMaze.possibilitiesToString());
            
            // POSITION STATUS
            System.out.println("Current: " + myMaze.getCurrent());
            System.out.println("Path: \n" + myMaze.pathToString());
            
            System.out.println("Position: " + myMaze.getPosition(0, 3));

            // REPRINT MAZE
            System.out.println(myMaze.toString());
        }
        catch (Exception e) {}

    }
}

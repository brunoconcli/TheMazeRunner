import java.io.*;
import Colors.Colors;

public class App {
    static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void clear() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void run() throws Exception {
        String usersFile;
        clear();

        System.out.println("Copy below the file which contains the maze: ");
        usersFile = bufferedReader.readLine();

        File file = new File(usersFile);
        Reader myReader = new Reader(file);
        Maze myMaze = new Maze(myReader.getBounds(), myReader.getBounds());

        for (int row = 0; row<myMaze.getHeight(); row++) {
            myReader.readLine();
            for (int col = 0; col<myMaze.getWidth(); col++) 
                myMaze.setChar(row, col, myReader.getCharInCol(col));   
        }

        myMaze.start();

        int steps = 0;
        long startTime = System.currentTimeMillis();
        for (;;) {
            if (!myMaze.getCurrent().equals(myMaze.getExit())) {
                myMaze.move();
            }
            else break;
            
            steps ++;
        }

        long endTime = System.currentTimeMillis();
        System.out.println("\n\nTo String():\n"+ myMaze.toString());
        System.out.println(Colors.RESET+
                            "Congrats you've made it!\nTotal steps: " + steps + 
                            "\nTotal ms: " + 
                            (endTime - startTime));
        
        System.out.println(Colors.GREEN_BOLD + "Press [ENTER] key to continue..." + Colors.RESET);
        bufferedReader.readLine();
    }
    
    public static void main(String[] args) {
        try {
            run();
            String option;
        while(true) {
            System.out.println(Colors.WHITE_BOLD + "Thank you for playing!\nWould you like to rerun?(Y/N): " + Colors.RESET);
            option = bufferedReader.readLine();
            switch(option.toUpperCase()) {
                case "Y":
                    main(args);
                    break;
                case "N":
                    System.out.println(Colors.CYAN + "Thanks for playing!" + Colors.RESET);
                    break;
                default:
                    System.out.println("Invalid option");
                    
                    break;
                }
            break;
        }

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            main(args);
        }
    }
}

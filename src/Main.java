import Colors.Colors;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            App.run();
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
            System.out.println(Colors.GREEN_BOLD + "Press [ENTER] key to continue..." + Colors.RESET);
            bufferedReader.readLine();
            main(args);
        }
    }
}

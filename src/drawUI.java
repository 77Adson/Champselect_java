// drawUI.java
import java.util.*;

public class drawUI {
    public static void showChampionSelect(List<String> availableChampions, List<String> team, String user) {
        System.out.println(user + "'s Turn");
        System.out.println("Available Champions: " + availableChampions);
        System.out.println(user + "'s Team: " + team);
    }

    public static String getChampionChoice(List<String> availableChampions) {
        try (Scanner scanner = new Scanner(System.in)) {
            String choice;
            do {
                System.out.print("Choose a champion: ");
                choice = scanner.nextLine();
            } while (!availableChampions.contains(choice));
            return choice;
        }
    }
}
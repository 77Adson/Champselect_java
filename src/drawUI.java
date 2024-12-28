// drawUI.java
import java.util.*;

public class drawUI {
    public static void showChampionSelect(List<String> availableChampions, List<String> team, List<String> enemyTeam) {
        System.out.println("Available Champions: " + availableChampions);
        System.out.println("Your team: " + team  + "\t" + enemyTeam + " :Enemy Team");
    }



    @SuppressWarnings("resource")
    public static String getChampionChoice(List<String> availableChampions) {
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            System.out.print("Choose a champion: ");
            choice = scanner.nextLine();
        } while (!availableChampions.contains(choice));
        return choice;
    }
}
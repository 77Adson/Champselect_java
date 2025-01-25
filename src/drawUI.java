import java.util.*;

public class drawUI {
    public static void showChampionSelect(List<String> availableChampions, List<String> team, List<String> enemyTeam) {
        System.out.println("\n=== Champion Select ===");
        System.out.println("Available Champions: " + availableChampions);
        System.out.println("Your Team: " + team + "\tEnemy Team: " + enemyTeam);
    }

    public static String getChampionChoice(List<String> availableChampions) {
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            System.out.print("Choose a champion: ");
            choice = scanner.nextLine();
            if (!availableChampions.contains(choice)) {
                System.out.println("Invalid choice. Try again.");
            }
        } while (!availableChampions.contains(choice));
        return choice;
    }
}

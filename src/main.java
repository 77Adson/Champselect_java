import java.io.*;
import java.net.Socket;
import java.util.*;

public class main {
    private static final List<String> champions = Arrays.asList("Ch1", "Ch2", "Ch3", "Ch4", "Ch5", "Ch6", "Ch7", "Ch8", "Ch9", "Ch10");
    private static int teamSizeLimit;
    private static int banNumber;

    public static void startChampionSelect(Socket socket, boolean isServer, Map<String, Integer> settings) {
        teamSizeLimit = settings.get("teamSizeLimit");
        banNumber = settings.get("banNumber");

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            List<String> myTeam = new ArrayList<>();
            List<String> enemyTeam = new ArrayList<>();
            List<String> availableChampions = new ArrayList<>(champions);

            boolean turn = isServer;

            System.out.println("Team size limit: " + teamSizeLimit);
            System.out.println("Ban number: " + banNumber);

            // Ban phase
            for (int i = 0; i < banNumber; i++) {
                if (turn) {
                    String ban = drawUI.getChampionChoice(availableChampions);
                    System.out.println("You banned: " + ban);
                    availableChampions.remove(ban);
                    out.println("BAN:" + ban);
                } else {
                    System.out.println("Waiting for opponent's ban...");
                    String response = in.readLine();
                    if (response.startsWith("BAN:")) {
                        String ban = response.substring(4);
                        availableChampions.remove(ban);
                        System.out.println("Opponent banned: " + ban);
                    }
                }
                turn = !turn;
            }

            // Pick phase
            while (myTeam.size() < teamSizeLimit || enemyTeam.size() < teamSizeLimit) {
                drawUI.showChampionSelect(availableChampions, myTeam, enemyTeam);

                if (turn) {
                    String choice = drawUI.getChampionChoice(availableChampions);
                    myTeam.add(choice);
                    availableChampions.remove(choice);
                    out.println(choice);
                } else {
                    System.out.println("Waiting for opponent's choice...");
                    String response = in.readLine();
                    enemyTeam.add(response);
                    availableChampions.remove(response);
                    System.out.println("Opponent picked: " + response);
                }
                turn = !turn;
            }

            saveTeamsToFile(myTeam, enemyTeam);
            System.out.println("Teams saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveTeamsToFile(List<String> serverTeam, List<String> clientTeam) {
        try (PrintWriter writer = new PrintWriter("teams.txt")) {
            writer.println("Server Team: " + serverTeam);
            writer.println("Client Team: " + clientTeam);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

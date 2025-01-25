// main.java
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


            while (myTeam.size() < 4 || enemyTeam.size() < 4) {
                System.out.println(isServer ? "I'm server" : "I'm client");
                drawUI.showChampionSelect(availableChampions, myTeam, enemyTeam);

                // Selecting turn
                if (turn) {
                    String choice = drawUI.getChampionChoice(availableChampions);
                    myTeam.add(choice);
                    availableChampions.remove(choice);
                    out.println(choice);

                // waiting turn
                } else {
                    System.out.println("Waiting for opponent's choice...");
                    String response = in.readLine();
                    enemyTeam.add(response);
                    availableChampions.remove(response);
                    System.out.println("Received champion from server: " + response);
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
// main.java
import java.io.*;
import java.net.Socket;
import java.util.*;


public class main {
    private static final List<String> champions = Arrays.asList("Champion1", "Champion2", "Champion3", "Champion4", "Champion5");

    public static void startChampionSelect(Socket socket, boolean isServer) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            List<String> serverTeam = new ArrayList<>();
            List<String> clientTeam = new ArrayList<>();
            List<String> availableChampions = new ArrayList<>(champions);

            while (serverTeam.size() + clientTeam.size() < 4) {
                if (isServer) {
                    drawUI.showChampionSelect(availableChampions, serverTeam, "Server");
                    String choice = drawUI.getChampionChoice(availableChampions);
                    serverTeam.add(choice);
                    availableChampions.remove(choice);
                    out.println(choice);
                } else {
                    drawUI.showChampionSelect(availableChampions, clientTeam, "Client");
                    String choice = drawUI.getChampionChoice(availableChampions);
                    clientTeam.add(choice);
                    availableChampions.remove(choice);
                    out.println(choice);
                }
                isServer = !isServer;
            }

            saveTeamsToFile(serverTeam, clientTeam);
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
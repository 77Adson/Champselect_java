import java.io.*;
import java.net.Socket;
import java.util.*;

public class main {
    private static final List<String> champions = Arrays.asList("Ch1", "Ch2", "Ch3", "Ch4", "Ch5", "Ch6", "Ch7", "Ch8", "Ch9", "Ch10", "Ch11");
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

            List<String> myBans = new ArrayList<>();  // Lista zbanowanych postaci przez moją drużynę
            List<String> enemyBans = new ArrayList<>();  // Lista zbanowanych postaci przez drużynę przeciwnika

            boolean turn = isServer;

            System.out.println("Team size limit: " + teamSizeLimit);
            System.out.println("Ban number: " + banNumber);

            // Create the GUI
            drawUI gui = new drawUI(availableChampions, myTeam, enemyTeam, myBans, enemyBans);

            // Ban phase
            for (int i = 0; i < banNumber * 2; i++) {
                if (turn) {
                    System.out.println("Your turn to ban...");
                    gui.unlockButtons();
                    String ban = gui.getChampionChoice("Ban a champion");
                    System.out.println("You banned: " + ban);
                    availableChampions.remove(ban);
                    myBans.add(ban);  // Dodajemy bana do listy moich banów
                    out.println("BAN:" + ban);
                } else {
                    System.out.println("Waiting for opponent's ban...");
                    gui.lockButtons();
                    String response = in.readLine();
                    if (response.startsWith("BAN:")) {
                        String ban = response.substring(4);
                        availableChampions.remove(ban);
                        enemyBans.add(ban);  // Dodajemy bana do listy banów przeciwnika
                        System.out.println("Opponent banned: " + ban);
                    }
                }
                turn = !turn;
                gui.updateUI();
            }

            // Pick phase
            while (myTeam.size() < teamSizeLimit || enemyTeam.size() < teamSizeLimit) {
                if (turn) {
                    System.out.println("Your turn to pick...");
                    gui.unlockButtons();
                    String choice = gui.getChampionChoice("Pick a champion");
                    myTeam.add(choice);
                    availableChampions.remove(choice);
                    out.println(choice);
                } else {
                    System.out.println("Waiting for opponent's choice...");
                    gui.lockButtons();
                    String response = in.readLine();
                    enemyTeam.add(response);
                    availableChampions.remove(response);
                    System.out.println("Opponent picked: " + response);
                }
                turn = !turn;
                gui.updateUI();
            }

            // Po zakończeniu fazy wyboru bohaterów
            gui.closeWindowAndShowSummary(myBans, enemyBans);  // Wywołanie metody po zakończeniu banów i wyborów

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

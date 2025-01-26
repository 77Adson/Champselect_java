import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class main {
    private static final List<Champion> champions = new ArrayList<>();
    private static int teamSizeLimit;
    private static int banNumber;

    static {
        // Dodajemy przykładowych championów
        champions.add(new Champion("pepe", "pepe.png"));
        champions.add(new Champion("amongus", "amongus.png"));
        champions.add(new Champion("stanczyk", "stanczyk.png"));
        champions.add(new Champion("maxwell", "maxwell.png"));
        champions.add(new Champion("freddy", "freddy.png"));
        champions.add(new Champion("richard", "richard.png"));
        champions.add(new Champion("muscleman", "muscleman.png"));
        champions.add(new Champion("walterwhite", "walterwhite.png"));
        champions.add(new Champion("vegeta", "vegeta.png"));
        champions.add(new Champion("frieza", "frieza.png"));
    }

    public static void startChampionSelect(Socket socket, boolean isServer, Map<String, Integer> settings) {
        teamSizeLimit = settings.get("teamSizeLimit");
        banNumber = settings.get("banNumber");
        banNumber *= 2;

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            List<Champion> myTeam = new ArrayList<>();
            List<Champion> enemyTeam = new ArrayList<>();
            List<Champion> availableChampions = new ArrayList<>(champions);

            List<Champion> myBans = new ArrayList<>();
            List<Champion> enemyBans = new ArrayList<>();
            boolean turn = isServer;

            // Tworzymy GUI
            drawUI gui = new drawUI(availableChampions, myTeam, enemyTeam, myBans, enemyBans);

            // Faza banów
            for (int i = 0; i < banNumber; i++) {
                if (turn) {
                    System.out.println("Your turn to ban...");
                    gui.unlockButtons();
                    String ban = gui.getChampionChoice("Ban a champion");
                    Champion bannedChampion = findChampionByName(ban);
                    myBans.add(bannedChampion);
                    availableChampions.remove(bannedChampion);
                    out.println("BAN:" + ban);
                } else {
                    System.out.println("Waiting for opponent's ban...");
                    gui.lockButtons();
                    String response = in.readLine();
                    if (response.startsWith("BAN:")) {
                        String ban = response.substring(4);
                        Champion bannedChampion = findChampionByName(ban);
                        enemyBans.add(bannedChampion);
                        availableChampions.remove(bannedChampion);
                    }
                }
                turn = !turn;
                gui.updateUI();
            }

            // Faza wyboru
            while (myTeam.size() < teamSizeLimit || enemyTeam.size() < teamSizeLimit) {
                if (turn) {
                    System.out.println("Your turn to pick...");
                    gui.unlockButtons();
                    String choice = gui.getChampionChoice("Pick a champion");
                    Champion pickedChampion = findChampionByName(choice);
                    myTeam.add(pickedChampion);
                    availableChampions.remove(pickedChampion);
                    out.println(choice);
                } else {
                    System.out.println("Waiting for opponent's choice...");
                    gui.lockButtons();
                    String response = in.readLine();
                    enemyTeam.add(findChampionByName(response));
                    availableChampions.remove(findChampionByName(response));
                }
                turn = !turn;
                gui.updateUI();
            }

            // Zapisujemy teamy do pliku
            gui.closeWindowAndShowSummary(myBans, enemyBans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Champion findChampionByName(String name) {
        for (Champion champion : champions) {
            if (champion.getName().equals(name)) {
                return champion;
            }
        }
        return null;
    }
}

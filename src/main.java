import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class main {
    private static final List<Champion> champions = new ArrayList<>();
    private static int teamSizeLimit;
    private static int banNumber;

    static {
        // Dodajemy przykładowych championów
        champions.add(new Champion("pepe", "pepe.png"));
        champions.add(new Champion("amongus", "amongus.png"));
        champions.add(new Champion("stanczyk", "stanczyk.jpg"));
        champions.add(new Champion("maxwell", "maxwell.png"));
        champions.add(new Champion("freddy", "freddy.png"));
        champions.add(new Champion("richard", "richard.png"));
        champions.add(new Champion("muscleman", "muscleman.png"));
        champions.add(new Champion("walterwhite", "walt.jpg"));
        champions.add(new Champion("vegeta", "vegeta.jpg"));
        champions.add(new Champion("frieza", "frieza.png"));
        champions.add(new Champion("trump", "trump.jpg"));
        champions.add(new Champion("joebiden", "joebiden.png"));
        champions.add(new Champion("goku", "goku.jpg"));
        champions.add(new Champion("shrek", "shrek.png"));
        champions.add(new Champion("donkey", "donkey.png"));
        champions.add(new Champion("batman", "batman.jpg"));
        champions.add(new Champion("joker", "joker.png"));
        champions.add(new Champion("spiderman", "spiderman.jpg"));
        champions.add(new Champion("saitama", "saitama.png"));
        champions.add(new Champion("genos", "genos.jpg"));
        champions.add(new Champion("sonic", "sonic.png"));
        champions.add(new Champion("knuckles", "knuckles.jpg"));
        champions.add(new Champion("tails", "tails.png"));
        champions.add(new Champion("mario", "mario.jpg"));
        champions.add(new Champion("luigi", "luigi.png"));
        champions.add(new Champion("wario", "wario.jpg"));
        champions.add(new Champion("yoshi", "yoshi.png"));
        champions.add(new Champion("pikachu", "pikachu.jpg"));
        champions.add(new Champion("charizard", "charizard.png"));
        champions.add(new Champion("link", "link.jpg"));
        champions.add(new Champion("zelda", "zelda.png"));
        champions.add(new Champion("kirby", "kirby.png"));
        champions.add(new Champion("metaknight", "metaknight.jpg"));
        champions.add(new Champion("samus", "samus.png"));
        champions.add(new Champion("masterchief", "masterchief.jpg"));
        champions.add(new Champion("kratos", "kratos.png"));
        champions.add(new Champion("aloy", "aloy.jpg"));
        champions.add(new Champion("lara", "lara.png"));
        champions.add(new Champion("geralt", "geralt.jpg"));
        champions.add(new Champion("yennifer", "yennifer.png"));
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
                    gui.updateLabel(turn, true);
                    gui.unlockButtons();
                    String ban = gui.getChampionChoice("Ban a champion");
                    Champion bannedChampion = findChampionByName(ban);
                    myBans.add(bannedChampion);
                    availableChampions.remove(bannedChampion);
                    out.println("BAN:" + ban);
                } else {
                    System.out.println("Waiting for opponent's ban...");
                    gui.updateLabel(false, true);
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
                    gui.updateLabel(turn, false);
                    gui.unlockButtons();
                    String choice = gui.getChampionChoice("Pick a champion");
                    Champion pickedChampion = findChampionByName(choice);
                    myTeam.add(pickedChampion);
                    availableChampions.remove(pickedChampion);
                    out.println(choice);
                } else {
                    System.out.println("Waiting for opponent's choice...");
                    gui.updateLabel(false, false);
                    gui.lockButtons();
                    String response = in.readLine();
                    enemyTeam.add(findChampionByName(response));
                    availableChampions.remove(findChampionByName(response));
                }
                turn = !turn;
                gui.updateUI();
            }

            // Zapisujemy teamy do pliku
            saveTeamsToFile(myTeam, enemyTeam, myBans, enemyBans);
            gui.closeWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveTeamsToFile(List<Champion> myTeam, List<Champion> enemyTeam, List<Champion> myBans, List<Champion> enemyBans) {
        try (PrintWriter writer = new PrintWriter("teams.txt")) {
            writer.println("My Team: " + myTeam.stream().map(Champion::getName).collect(Collectors.joining(", ")));
            writer.println("Enemy Team: " + enemyTeam.stream().map(Champion::getName).collect(Collectors.joining(", ")));
            writer.println("My Bans: " + myBans.stream().map(Champion::getName).collect(Collectors.joining(", ")));
            writer.println("Enemy Bans: " + enemyBans.stream().map(Champion::getName).collect(Collectors.joining(", ")));
        } catch (IOException e) {
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

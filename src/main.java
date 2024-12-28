// main.java
import java.io.*;
import java.net.Socket;
import java.util.*;


public class main {
    private static final List<String> champions = Arrays.asList("Ch1", "Ch2", "Ch3", "Ch4", "Ch5", "Ch6", "Ch7", "Ch8", "Ch9", "Ch10");

    /**
     * Starts the champion select process for a game of League of Legends.
     *
     * This method takes a socket and a boolean as parameters. The socket is
     * used to communicate with the other player, and the boolean is used to
     * determine whether the player is the server or the client.
     *
     * The champion select process is as follows:
     *  1. The server and client each select a champion from the list of
     *     available champions.
     *  2. The server and client each send their chosen champion to the
     *     other player.
     *  3. The server and client each add the received champion to their
     *     respective teams.
     *  4. Steps 1-3 are repeated until each team has 4 champions.
     *  5. The teams are saved to a file.
     *
     * @param socket the socket used to communicate with the other player
     * @param turn a boolean indicating whether the player is the server
     *                or the client
     */
    public static void startChampionSelect(Socket socket, boolean isServer) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            List<String> myTeam = new ArrayList<>();
            List<String> enemyTeam = new ArrayList<>();
            List<String> availableChampions = new ArrayList<>(champions);
            
            boolean turn = isServer;


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
                    String response = in.readLine();
                    if (response == null) {
                        System.out.println("Server sent null champion");
                        // handle this error case
                    } else {
                        enemyTeam.add(response);
                        availableChampions.remove(response);
                        System.out.println("Received champion from server: " + response);
                    }
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
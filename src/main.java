// main.java
import java.io.*;
import java.net.Socket;
import java.util.*;

public class main {
    public static void runGame(Socket socket, boolean isServer) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            List<String> champions = Arrays.asList("Champion1", "Champion2", "Champion3", "Champion4");
            List<String> selected = new ArrayList<>();
            try (Scanner scanner = new Scanner(System.in)) {
                while (selected.size() < champions.size()) {
                    if ((isServer && selected.size() % 2 == 0) || (!isServer && selected.size() % 2 != 0)) {
                        System.out.println("Available champions: " + champions);
                        System.out.print("Select a champion: ");
                        String choice;

                        while (true) {
                            choice = scanner.nextLine();
                            if (champions.contains(choice) && !selected.contains(choice)) {
                                break;
                            }
                            System.out.print("Invalid choice. Try again: ");
                        }

                        selected.add(choice);
                        out.println(choice);
                    } else {
                        System.out.println("Waiting for opponent...");
                        String opponentChoice = in.readLine();
                        selected.add(opponentChoice);
                        System.out.println("Opponent selected: " + opponentChoice);
                    }
                }
            } finally {
                socket.close();
            }

            System.out.println("Selection complete: " + selected);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
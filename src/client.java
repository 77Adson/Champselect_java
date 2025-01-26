import java.io.*;
import java.net.*;
import java.util.*;

public class client {
    public static void main(String[] args) {
        try (Socket serverSocket = new Socket("localhost", 12345)) {
            System.out.println("Connected to server.");

            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            String settingsString = in.readLine();

            // Parse settings string into a map
            Map<String, Integer> settings = new HashMap<>();
            for (String pair : settingsString.split(",")) {
                String[] keyValue = pair.split(":");
                settings.put(keyValue[0], Integer.parseInt(keyValue[1]));
            }

            main.startChampionSelect(serverSocket, false, settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

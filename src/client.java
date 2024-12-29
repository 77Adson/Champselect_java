// client.java
import java.io.*;
import java.net.*;
import java.util.*;



public class client {
    public static void main(String[] args) {
        try (Socket serverSocket = new Socket("localhost", 12345)) {
            System.out.println("Connected to server.");

            // Receive settings from server
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            // int teamSizeLimit = Integer.parseInt(in.readLine());
            // int[] teamSizeLimit = Arrays.stream(in.readLine().split(",")).mapToInt(Integer::parseInt).toArray();
            String settingsString = in.readLine();
            Map<String, Integer> settings;
            if (settingsString != null) {
                String[] settingsArray = settingsString.split(",");
                settings = new HashMap<>();
                for (int i = 0; i < settingsArray.length; i += 2) {
                    settings.put(settingsArray[i], Integer.parseInt(settingsArray[i + 1]));
                }
            }

            main.startChampionSelect(serverSocket, false, settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
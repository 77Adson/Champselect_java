// server.java
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class server {
    private static Map<String, Integer> settings = new HashMap<>();

    public static void get_settings(Map<String, Integer> settings) {
        server.settings = settings;
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is waiting for a client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            // Send teamSizeLimit to client
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(settings);

            main.startChampionSelect(clientSocket, true, settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
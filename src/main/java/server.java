package main.java;
import java.io.*;
import java.net.*;
import java.util.*;

public class server {
    private static Map<String, Integer> settings = new HashMap<>();

    public static void getSettings(Map<String, Integer> settings) {
        server.settings = settings;
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is waiting for a client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            // Send settings to client in plain text
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("teamSizeLimit:" + settings.get("teamSizeLimit") + ",banNumber:" + settings.get("banNumber"));

            main.startChampionSelect(clientSocket, true, settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

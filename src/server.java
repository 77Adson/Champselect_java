// server.java
import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is waiting for a client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            main.startChampionSelect(clientSocket, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
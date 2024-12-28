// server.java
import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started. Waiting for client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            main.runGame(clientSocket, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
// client.java
import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)) {
            System.out.println("Connected to server.");

            main.runGame(socket, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
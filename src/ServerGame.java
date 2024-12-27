import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class ServerGame {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ArrayList<String> team = new ArrayList<>();

    public void startServer() {
        try {
            serverSocket = new ServerSocket(12345);
            System.out.println("Waiting for client...");
            clientSocket = serverSocket.accept();
            System.out.println("Client connected.");
            new GameUI("Server", team, clientSocket.getOutputStream()).createUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
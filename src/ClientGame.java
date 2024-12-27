import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

class ClientGame {
    private Socket socket;
    private ArrayList<String> team = new ArrayList<>();

    public void startClient() {
        try {
            socket = new Socket("localhost", 12345);
            System.out.println("Connected to server.");
            new GameUI("Client", team, socket.getOutputStream()).createUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

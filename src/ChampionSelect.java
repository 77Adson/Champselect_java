import javax.swing.*;

public class ChampionSelect {

    public static void main(String[] args) {
        String[] options = {"Server", "Client"};
        int choice = JOptionPane.showOptionDialog(null, "Choose role:", "Champion Select",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            new ServerGame().startServer();
        } else if (choice == 1) {
            new ClientGame().startClient();
        }
    }
}
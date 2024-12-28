// start.java
import javax.swing.*;

public class start {
    public static void main(String[] args) {
        String[] options = {"Server", "Client"};
        int choice = JOptionPane.showOptionDialog(null,
                "Start as:",
                "Champion Select",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            server.main(null);
        } else if (choice == 1) {
            client.main(null);
        } else {
            System.exit(0);
        }
    }
}
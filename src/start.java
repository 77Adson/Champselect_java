// start.java
import javax.swing.*;

public class start {
    public static void main(String[] args) {
        String[] options = {"Start Server", "Start Client"};
        int choice = JOptionPane.showOptionDialog(null, "Choose an option", "Champion Select",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            server.main(new String[]{});
        } else if (choice == 1) {
            client.main(new String[]{});
        }
    }
}
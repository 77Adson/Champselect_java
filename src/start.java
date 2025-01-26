import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class start {
    private static Map<String, Integer> settings = new HashMap<>();

    public static void main(String[] args) {
        String[] options = {"Start Server", "Start Client"};
        int choice = JOptionPane.showOptionDialog(null, "Choose an option", "Champion Select",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            setSettings();
            server.getSettings(settings);
            server.main(new String[]{});
        } else if (choice == 1) {
            client.main(new String[]{});
        }
    }

    public static void setSettings() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel teamSizeLabel = new JLabel("Enter team size limit:");
        JTextField teamSizeField = new JTextField("2", 5);

        JLabel banNumberLabel = new JLabel("Enter ban number:");
        JTextField banNumberField = new JTextField("2", 5);

        panel.add(teamSizeLabel);
        panel.add(teamSizeField);
        panel.add(banNumberLabel);
        panel.add(banNumberField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Settings", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            settings.put("teamSizeLimit", Integer.parseInt(teamSizeField.getText()));
            settings.put("banNumber", Integer.parseInt(banNumberField.getText()));
        }
    }
}

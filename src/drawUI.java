import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class drawUI {
    private final JFrame frame;
    private final JPanel buttonPanel;
    private final JTextArea banArea;
    private final JTextArea myTeamArea;
    private final JTextArea enemyTeamArea;
    private final List<String> myTeam;
    private final List<String> enemyTeam;
    private final List<String> availableChampions;

    public drawUI(List<String> availableChampions, List<String> myTeam, List<String> enemyTeam, int teamSizeLimit, int banNumber) {
        this.myTeam = myTeam;
        this.enemyTeam = enemyTeam;
        this.availableChampions = availableChampions;

        // Frame setup
        frame = new JFrame("Champion Select");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Buttons for champions
        buttonPanel = new JPanel(new GridLayout(0, 5, 5, 5)); // Dynamic grid layout
        for (String champion : availableChampions) {
            JButton champButton = new JButton(champion);
            champButton.setFont(new Font("Arial", Font.BOLD, 12));
            champButton.addActionListener(new ChampionButtonListener(champion));
            buttonPanel.add(champButton);
        }
        JScrollPane buttonScrollPane = new JScrollPane(buttonPanel);

        // Team and ban areas
        banArea = new JTextArea("Bans:\n");
        banArea.setEditable(false);
        myTeamArea = new JTextArea("My Team:\n");
        myTeamArea.setEditable(false);
        enemyTeamArea = new JTextArea("Enemy Team:\n");
        enemyTeamArea.setEditable(false);

        JPanel infoPanel = new JPanel(new GridLayout(1, 3));
        infoPanel.add(new JScrollPane(banArea));
        infoPanel.add(new JScrollPane(myTeamArea));
        infoPanel.add(new JScrollPane(enemyTeamArea));

        // Add components to frame
        frame.add(buttonScrollPane, BorderLayout.CENTER);
        frame.add(infoPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private String selectedChampion = null;

    public String getChampionChoice(String actionMessage) {
        selectedChampion = null;
        JOptionPane.showMessageDialog(frame, actionMessage, "Action Required", JOptionPane.INFORMATION_MESSAGE);

        while (selectedChampion == null) {
            try {
                Thread.sleep(100); // Wait until a button is pressed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return selectedChampion;
    }

    public void lockButtons() {
        Component[] components = buttonPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                ((JButton) component).setEnabled(false);
            }
        }
    }

    public void unlockButtons() {
        Component[] components = buttonPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                ((JButton) component).setEnabled(true);
            }
        }
    }

    public void updateUI() {
        banArea.setText("Bans:\n" + String.join(", ", availableChampions));
        myTeamArea.setText("My Team:\n" + String.join(", ", myTeam));
        enemyTeamArea.setText("Enemy Team:\n" + String.join(", ", enemyTeam));

        // Refresh button list
        buttonPanel.removeAll();
        for (String champion : availableChampions) {
            JButton champButton = new JButton(champion);
            champButton.setFont(new Font("Arial", Font.BOLD, 12));
            champButton.addActionListener(new ChampionButtonListener(champion));
            buttonPanel.add(champButton);
        }
        buttonPanel.revalidate();
        buttonPanel.repaint();

        frame.repaint();
    }

    public void closeWindowAndShowSummary(List<String> myBans, List<String> enemyBans) {
        frame.dispose();  // Close the main window

        // Show the summary table
        showSummaryTable(myBans, enemyBans);
    }

    public void showSummaryTable(List<String> myBans, List<String> enemyBans) {
        // Create the table to show the summary
        String[] columns = {"Team", "Bans", "Picks"};
        Object[][] data = {
                {"My Team", String.join(", ", myBans), String.join(", ", myTeam)},
                {"Enemy Team", String.join(", ", enemyBans), String.join(", ", enemyTeam)}
        };

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a new JFrame to show the summary
        JFrame summaryFrame = new JFrame("Champion Select Summary");
        summaryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        summaryFrame.setSize(600, 300);
        summaryFrame.add(scrollPane);
        summaryFrame.setVisible(true);
    }

    private class ChampionButtonListener implements ActionListener {
        private final String champion;

        public ChampionButtonListener(String champion) {
            this.champion = champion;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (availableChampions.contains(champion)) {
                selectedChampion = champion;
                availableChampions.remove(champion);
                Component[] components = buttonPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof JButton && ((JButton) component).getText().equals(champion)) {
                        buttonPanel.remove(component);
                        break;
                    }
                }
                buttonPanel.revalidate();
                buttonPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Champion is already selected or banned.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

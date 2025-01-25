import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class drawUI {
    private final JFrame frame;
    private final JPanel buttonPanel;
    private final JTextArea myBanArea;
    private final JTextArea enemyBanArea;
    private final JTextArea myTeamArea;
    private final JTextArea enemyTeamArea;
    private final List<String> myTeam;
    private final List<String> enemyTeam;
    private final List<String> availableChampions;
    private final List<String> myBans;
    private final List<String> enemyBans;


    public drawUI(List<String> availableChampions, List<String> myTeam, List<String> enemyTeam, List<String> myBans, List<String> enemyBans) {
        this.myTeam = myTeam;
        this.enemyTeam = enemyTeam;
        this.availableChampions = availableChampions;
        this.myBans = myBans;
        this.enemyBans = enemyBans;

        // Frame setup
        frame = new JFrame("Champion Select");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Left panel(My Team and Bans)
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        myBanArea = new JTextArea("Bans:\n" + String.join(", ", myBans));
        myBanArea.setEditable(false);
        myTeamArea = new JTextArea("My Team:\n" + String.join(", ", myTeam));
        myTeamArea.setEditable(false);
        leftPanel.add(new JScrollPane(myBanArea));
        leftPanel.add(new JScrollPane(myTeamArea));
        

        // Right panel(Enemy Team and Bans)
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        enemyBanArea = new JTextArea("Bans:\n" + String.join(", ", enemyBans));
        enemyBanArea.setEditable(false);
        enemyTeamArea = new JTextArea("Enemy Team:\n" + String.join(", ", enemyTeam));
        enemyTeamArea.setEditable(false);
        rightPanel.add(new JScrollPane(enemyBanArea));
        rightPanel.add(new JScrollPane(enemyTeamArea));

        // Panel with fixed rows of 5 buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 5, 10, 10)); // 5 columns, unlimited rows
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        drawButtonPanel();

        // Wrap the button panel in a JScrollPane
        JScrollPane buttonScrollPane = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        buttonScrollPane.setPreferredSize(new Dimension(550, 400));
        buttonScrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling


        // Add components to frame
        frame.add(buttonScrollPane, BorderLayout.CENTER);
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.EAST);

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

    void drawButtonPanel() {
        buttonPanel.removeAll();
        for (String champion : availableChampions) {
            JButton champButton = new JButton(champion);
            champButton.setFont(new Font("Arial", Font.BOLD, 12));
            champButton.setPreferredSize(new Dimension(100, 100)); // Ensure square buttons
            champButton.setMaximumSize(new Dimension(100, 100));  // Optional, to enforce square buttons
            champButton.addActionListener(new ChampionButtonListener(champion));
            buttonPanel.add(champButton);
        }
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    public void updateUI() {
        // Update the text areas
        myBanArea.setText("Bans:\n" + String.join("\n", myBans));
        enemyBanArea.setText("Bans:\n" + String.join("\n", enemyBans));
        myTeamArea.setText("My Team:\n" + String.join("\n", myTeam));
        enemyTeamArea.setText("Enemy Team:\n" + String.join("\n", enemyTeam));

        // Refresh button list
        buttonPanel.removeAll();
        drawButtonPanel();
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
                JOptionPane.showMessageDialog(frame, "Wait for your turn.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

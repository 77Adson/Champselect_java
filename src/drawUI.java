import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class drawUI {
    private final JFrame frame;
    private final JPanel buttonPanel;
    private final JPanel myBanArea;
    private final JPanel myTeamArea;
    private final JPanel enemyBanArea;
    private final JPanel enemyTeamArea;
    private final List<Champion> myTeam;
    private final List<Champion> enemyTeam;
    private final List<Champion> availableChampions;
    private final List<Champion> myBans;
    private final List<Champion> enemyBans;


    public drawUI(List<Champion> availableChampions, List<Champion> myTeam, List<Champion> enemyTeam, List<Champion> myBans, List<Champion> enemyBans) {
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
        myBanArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        myTeamArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        leftPanel.add(new JScrollPane(myBanArea));
        leftPanel.add(new JScrollPane(myTeamArea));
        

        // Right panel(Enemy Team and Bans)
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        enemyBanArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        enemyTeamArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rightPanel.add(new JScrollPane(enemyBanArea));
        rightPanel.add(new JScrollPane(enemyTeamArea));

        // draw teams area
        drawTeamsArea();

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
        // buttonPanel.removeAll();
        for (Champion champion : availableChampions) {
            JButton champButton = new JButton(champion.getName());
            champButton.setIcon(new ImageIcon(champion.getImage().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
            champButton.setFont(new Font("Arial", Font.BOLD, 12));
            champButton.addActionListener(new ChampionButtonListener(champion));
            buttonPanel.add(champButton);
        }
        // buttonPanel.revalidate();
        // buttonPanel.repaint();
    }

    void drawTeamsArea() {
        // draw my team
        for (Champion champion : myTeam) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.add(new JLabel(champion.getName()));
            panel.add(new JLabel(new ImageIcon(champion.getImage().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH))));
            myTeamArea.add(panel);
        }

        // draw my bans
        for (Champion champion : myBans) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.add(new JLabel(champion.getName()));
            panel.add(new JLabel(new ImageIcon(champion.getImage().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH))));
            myBanArea.add(panel);
        }

        // draw enemy team
        for (Champion champion : enemyTeam) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.add(new JLabel(champion.getName()));
            panel.add(new JLabel(new ImageIcon(champion.getImage().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH))));
            enemyTeamArea.add(panel);
        }

        // draw enemy bans
        for (Champion champion : enemyBans) {    
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.add(new JLabel(champion.getName()));
            panel.add(new JLabel(new ImageIcon(champion.getImage().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH))));
            enemyBanArea.add(panel);
        }
        
    }

    public void updateUI() {
        // Update the text areas
        myBanArea.removeAll();
        enemyBanArea.removeAll();
        myTeamArea.removeAll();
        enemyTeamArea.removeAll();
        drawTeamsArea();
        myBanArea.revalidate();
        myBanArea.repaint();
        enemyBanArea.revalidate();
        enemyBanArea.repaint();
        myTeamArea.revalidate();
        myTeamArea.repaint();
        enemyTeamArea.revalidate();
        enemyTeamArea.repaint();

        // Refresh button list
        buttonPanel.removeAll();
        drawButtonPanel();
        buttonPanel.revalidate();
        buttonPanel.repaint();

        frame.repaint();
    }

    public void closeWindowAndShowSummary(List<Champion> myBans, List<Champion> enemyBans) {
        // Zamknięcie głównego okna
        frame.dispose();

        // Tworzenie nowego okna podsumowującego
        JFrame summaryFrame = new JFrame("Summary");
        summaryFrame.setSize(400, 300);
        summaryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tworzymy panel z informacjami o banach
        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);

        // Tworzymy podsumowanie banów
        StringBuilder summaryText = new StringBuilder("Bans Summary:\n");
        summaryText.append("My Bans:\n");
        for (Champion ban : myBans) {
            summaryText.append("- ").append(ban.getName()).append("\n");
        }

        summaryText.append("\nEnemy Bans:\n");
        for (Champion ban : enemyBans) {
            summaryText.append("- ").append(ban.getName()).append("\n");
        }

        // Ustawienie tekstu w JTextArea
        summaryArea.setText(summaryText.toString());

        // Ustawienie scrolla
        JScrollPane scrollPane = new JScrollPane(summaryArea);
        summaryFrame.add(scrollPane);

        // Wyświetlamy okno podsumowujące
        summaryFrame.setVisible(true);
    }

    private class ChampionButtonListener implements ActionListener {
        private final Champion champion;

        public ChampionButtonListener(Champion champion) {
            this.champion = champion;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (availableChampions.contains(champion)) {
                selectedChampion = champion.getName();
                availableChampions.remove(champion);
                Component[] components = buttonPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof JButton && ((JButton) component).getText().equals(champion.getName())) {
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

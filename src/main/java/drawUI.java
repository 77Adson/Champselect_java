package main.java;
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
        frame.setSize(1100, 650);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        // top panel for bans
        JPanel topArea = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 row, 2 columns, 10px horizontal gap
        topArea.setBackground(Color.BLACK);
        topArea.setPreferredSize(new Dimension(800, 80)); // Adjust height to 100px (~16.6% of 600px)
        // Add components to top area
        myBanArea = new JPanel();
        myBanArea.setBackground(Color.GRAY);
        myBanArea.add(new JLabel("My Bans"));

        enemyBanArea = new JPanel();
        enemyBanArea.setBackground(Color.GRAY);
        enemyBanArea.add(new JLabel("Enemy Bans"));

        topArea.add(myBanArea);
        topArea.add(enemyBanArea);

        // bottom panel holding myteam, enemyteam and buttons
        // Bottom area (80-90% height)
        JPanel bottomArea = new JPanel(new BorderLayout());
        bottomArea.setBackground(Color.DARK_GRAY);

        // Left panel for my team
        myTeamArea = new JPanel();
        myTeamArea.setBackground(Color.GREEN);
        myTeamArea.setPreferredSize(new Dimension(160, 0)); // 20% of 800px width (example for initial size)
        myTeamArea.add(new JLabel("My team"));

        drawTeamsArea(myTeam, myBans, myTeamArea, myBanArea);

        // Right panel for enemy team
        enemyTeamArea = new JPanel();
        enemyTeamArea.setBackground(Color.PINK);
        enemyTeamArea.setPreferredSize(new Dimension(160, 0)); // 20% of 800px width
        enemyTeamArea.add(new JLabel("Enemy team"));

        drawTeamsArea(enemyTeam, enemyBans, enemyTeamArea, enemyBanArea);


        // middle panel for buttons 5 columns, unlimited rows
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.add(new JLabel("Available Champions"), BorderLayout.NORTH);
        middlePanel.setBackground(Color.orange);
        // Button panel
        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        drawButtonPanel();

        // Wrap the button panel in a JScrollPane
        JScrollPane buttonScrollPane = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        buttonScrollPane.setPreferredSize(new Dimension(550, 450));
        buttonScrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
        middlePanel.add(buttonScrollPane, BorderLayout.SOUTH);


        // Add components to frame
        bottomArea.add(middlePanel, BorderLayout.CENTER);
        bottomArea.add(myTeamArea, BorderLayout.WEST);
        bottomArea.add(enemyTeamArea, BorderLayout.EAST);

        frame.add(topArea, BorderLayout.NORTH);
        frame.add(bottomArea, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private String selectedChampion = null;

    public String getChampionChoice(String actionMessage) {
        selectedChampion = null;
        // JOptionPane.showMessageDialog(frame, actionMessage, "Action Required", JOptionPane.INFORMATION_MESSAGE);

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
        // Set up GridBagLayout for buttonPanel
        buttonPanel.setLayout(new GridBagLayout()); // Use GridBagLayout
        buttonPanel.removeAll(); // Clear any previous components
    
        // Create a GridBagConstraints object
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE; // No stretching, fixed size
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between buttons
    
        int row = 0;
        int col = 0;
    
        // Loop through the available champions to add buttons
        for (Champion champion : availableChampions) {
            JButton champButton = new JButton(champion.getName());
            champButton.setIcon(new ImageIcon(champion.getImage().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
            champButton.setFont(new Font("Arial", Font.BOLD, 12));
    
            // Set fixed size for the button
            champButton.setPreferredSize(new Dimension(100, 120)); // Fixed width and height
            champButton.setHorizontalTextPosition(SwingConstants.CENTER); // Center the text
            champButton.setVerticalTextPosition(SwingConstants.BOTTOM); // Place text below the icon
            champButton.addActionListener(new ChampionButtonListener(champion));
    
            // Set the position in the grid using GridBagConstraints
            gbc.gridx = col;
            gbc.gridy = row;
            
            // Add the button to the panel at the specified grid position
            buttonPanel.add(champButton, gbc);
    
            // Move to the next column
            col++;
    
            // After 5 buttons, move to the next row and reset column index
            if (col == 5) {
                col = 0;
                row++;
            }
        }
    
        // Revalidate and repaint to update the panel
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }
    

    void drawTeamsArea(List<Champion> team, List<Champion> bans, JPanel teamArea, JPanel banArea) {
        teamArea.removeAll();
        banArea.removeAll();
    
        for (Champion champion : team) {
            teamArea.add(createChampionPanel(champion, false));
        }
    
        for (Champion champion : bans) {
            banArea.add(createChampionPanel(champion, true));
        }
    
        teamArea.revalidate();
        banArea.revalidate();
        teamArea.repaint();
        banArea.repaint();
    }
    
    private JPanel createChampionPanel(Champion champion, boolean isBan) {
        JPanel champPanel = new JPanel();
        if (isBan) {
            champPanel.setBackground(Color.RED);
            JLabel imageLabel = new JLabel(new ImageIcon(champion.getImage().getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            champPanel.add(imageLabel);
        } else {
            JLabel imageLabel = new JLabel(new ImageIcon(champion.getImage().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
            champPanel.add(imageLabel);
        }
        champPanel.setLayout(new BoxLayout(champPanel, BoxLayout.Y_AXIS));
        JLabel nameLabel = new JLabel(champion.getName(), JLabel.CENTER);
        champPanel.add(nameLabel);

        return champPanel;
    }
    
    

    public void updateUI() {
        // Update the text areas
        drawTeamsArea(myTeam, myBans, myTeamArea, myBanArea);
        drawTeamsArea(enemyTeam, enemyBans, enemyTeamArea, enemyBanArea);


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

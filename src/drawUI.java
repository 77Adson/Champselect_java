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
    private final JLabel dynamicLabel;

    Color primaryColor = new Color(45, 45, 45); // Ciemnoszary
    Color secondaryColor = new Color(30, 30, 30); // Bardzo ciemnoszary
    Color accentColor = new Color(70, 130, 180); // Stalowy niebieski
    Color buttonBackground = new Color(50, 50, 50); // Przyciski w neutralnym kolorze
    Color buttonForeground = Color.WHITE; // Biały tekst na przyciskach


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
        topArea.setPreferredSize(new Dimension(800, 100)); // Adjust height to 100px (~16.6% of 600px)
        // Add components to top area
        myBanArea = new JPanel();
        JScrollPane myBanScrollPane = new JScrollPane(myBanArea, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        myBanScrollPane.setPreferredSize(new Dimension(400, 100));
        topArea.add(myBanScrollPane);

        enemyBanArea = new JPanel();
        JScrollPane enemyBanScrollPane = new JScrollPane(enemyBanArea, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        enemyBanScrollPane.setPreferredSize(new Dimension(400, 100));
        topArea.add(enemyBanScrollPane);

        // bottom panel holding myteam, enemyteam and buttons
        // Bottom area (80-90% height)
        JPanel bottomArea = new JPanel(new BorderLayout());

        // Left panel for my team
        myTeamArea = new JPanel();
        JScrollPane myTeamScrollPane = new JScrollPane(myTeamArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        myTeamScrollPane.setPreferredSize(new Dimension(160, 0)); // 20% of 800px width (example for initial size)
        bottomArea.add(myTeamScrollPane, BorderLayout.WEST);

        drawTeamsArea(myTeam, myBans, myTeamArea, myBanArea, true);

        // Right panel for enemy team
        enemyTeamArea = new JPanel();
        JScrollPane enemyTeamScrollPane = new JScrollPane(enemyTeamArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        enemyTeamScrollPane.setPreferredSize(new Dimension(160, 0)); // 20% of 800px width
        bottomArea.add(enemyTeamScrollPane, BorderLayout.EAST);

        drawTeamsArea(enemyTeam, enemyBans, enemyTeamArea, enemyBanArea, false);


        // middle panel
        JPanel middlePanel = new JPanel(new BorderLayout());

        // Dynamic label
        dynamicLabel = new JLabel("Available Champions", SwingConstants.CENTER); // Center the text
        dynamicLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Increase font size and make it bold
        dynamicLabel.setForeground(Color.WHITE); // Set the font color for visibility
        dynamicLabel.setOpaque(false); // Transparent background for the label
        middlePanel.add(dynamicLabel, BorderLayout.NORTH); // Add the label to the top

        // Button panel
        buttonPanel = new JPanel();
        buttonPanel.setBackground(primaryColor); // Tło przycisków
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Więcej marginesu

        drawButtonPanel();

        // Wrap the button panel in a JScrollPane
        JScrollPane buttonScrollPane = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        buttonScrollPane.setPreferredSize(new Dimension(550, 450));
        buttonScrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
        middlePanel.add(buttonScrollPane, BorderLayout.SOUTH);


        // Add components to frame
        bottomArea.add(middlePanel, BorderLayout.CENTER);

        frame.add(topArea, BorderLayout.NORTH);
        frame.add(bottomArea, BorderLayout.CENTER);

        // Zastosowanie kolorów do paneli
        frame.getContentPane().setBackground(primaryColor); // Tło główne
        topArea.setBackground(secondaryColor); // Tło panelu z banami
        bottomArea.setBackground(secondaryColor); // Tło dolnego panelu
        middlePanel.setBackground(primaryColor); // Tło środkowego panelu z przyciskami

        // Panele drużyn
        myTeamArea.setBackground(primaryColor); // Ciemnoszare tło dla drużyny
        enemyTeamArea.setBackground(primaryColor); // Ciemnoszare tło dla drużyny

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
            champButton.setBackground(buttonBackground); // Tło przycisku
            champButton.setForeground(buttonForeground); // Kolor tekstu
            champButton.setFont(new Font("Arial", Font.BOLD, 12)); // Wyraźniejsza czcionka
    
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
    

void drawTeamsArea(List<Champion> team, List<Champion> bans, JPanel teamArea, JPanel banArea, boolean isMyTeam) {
    // Set GridBagLayout for teamArea to center content horizontally and stack vertically
    teamArea.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    
    // Set GridBagLayout for banArea as before (FlowLayout for left or right alignment)
    if (isMyTeam) {
        banArea.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
    } else {
        banArea.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    }

    // Usuwamy poprzednie komponenty
    teamArea.removeAll();
    banArea.removeAll();

    // Dodajemy bohaterów do teamArea (stacked vertically and centered horizontally)
    gbc.gridx = 0;  // One column
    gbc.gridy = 0;  // Start at the top

    gbc.insets = new Insets(5, 10, 5, 10); // Add padding between buttons

    for (Champion champion : team) {
        JPanel championPanel = createChampionPanel(champion, false);

        // Set the GridBagConstraints to center horizontally and stack vertically
        gbc.anchor = GridBagConstraints.CENTER;  // Center horizontally
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Fill horizontally (optional)
        teamArea.add(championPanel, gbc);

        gbc.gridy++;  // Move to the next row (vertical stacking)
    }

    // Dodajemy bany do banArea (lewo-prawo)
    for (Champion champion : bans) {
        banArea.add(createChampionPanel(champion, true));
    }

    // Odświeżamy panele
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
            champPanel.setBackground(primaryColor); // Tło dla panelu championów
            JLabel imageLabel = new JLabel(new ImageIcon(champion.getImage().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
            champPanel.add(imageLabel);
        }
        champPanel.setLayout(new BoxLayout(champPanel, BoxLayout.Y_AXIS));
        JLabel nameLabel = new JLabel(champion.getName(), JLabel.CENTER);
        champPanel.add(nameLabel);

        champPanel.setBorder(BorderFactory.createLineBorder(accentColor, 2)); // Obwódka w kolorze akcentu
        nameLabel.setForeground(Color.WHITE); // Biały tekst na nazwach championów

        return champPanel;
    }
    
    

    public void updateUI() {
        // Update the text areas
        drawTeamsArea(myTeam, myBans, myTeamArea, myBanArea, true);
        drawTeamsArea(enemyTeam, enemyBans, enemyTeamArea, enemyBanArea, false);


        // Refresh button list
        buttonPanel.removeAll();
        drawButtonPanel();
        buttonPanel.revalidate();
        buttonPanel.repaint();

        frame.repaint();
    }

    public void updateLabel(boolean isMyTurn, boolean isBanPhase) {
        String prefix = isMyTurn ? "" : "Enemy is ";
        String action = isBanPhase ? "Banning" : "Choosing";
        dynamicLabel.setText(prefix + action + (isMyTurn ? ":" : "..."));
        dynamicLabel.revalidate();
        dynamicLabel.repaint();
    }

    public void closeWindow() {
        frame.dispose();
    }
    


    // Listener class for handling button clicks
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

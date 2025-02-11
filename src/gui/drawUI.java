package gui;

import javax.swing.*;
import java.awt.*;

public class drawUI {
    private final JFrame frame;
    private final JPanel leftPanel;
    private final JPanel centerPanel;
    private final JPanel rightPanel;

    Color primaryColor = new Color(45, 45, 45); // Ciemnoszary
    Color secondaryColor = new Color(30, 30, 30); // Bardzo ciemnoszary
    Color accentColor = new Color(70, 130, 180); // Stalowy niebieski
    Color buttonBackground = new Color(50, 50, 50); // Przyciski w neutralnym kolorze
    Color buttonForeground = Color.WHITE; // Biały tekst na przyciskach

    public drawUI() {
        frame = new JFrame("Champion Select");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());

        leftPanel = new JPanel();
        centerPanel = new JPanel();
        rightPanel = new JPanel();

        leftPanel.setBackground(primaryColor);
        centerPanel.setBackground(secondaryColor);
        rightPanel.setBackground(primaryColor);

        // Set up the layout constraints
        setupMainFrame();
        setupCenterPanel();
        setupSidePanel(leftPanel);
        setupSidePanel(rightPanel);

        frame.setVisible(true);

        // Add visible red borders for testing
        testing_borders();
    }

    public void closeWindow() {
        frame.dispose();
    }

    public void updateUI() {
        //pass
    }

    public void setupSidePanel(JPanel sidePanel) {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(primaryColor);
        JPanel teamPanel = new JPanel();
        teamPanel.setBackground(primaryColor);
    
        sidePanel.setLayout(new BorderLayout());
    
        // Set the preferred size of the top panel to 100px
        topPanel.setPreferredSize(new Dimension(0, 100)); // Height = 100px
        sidePanel.add(topPanel, BorderLayout.NORTH);
        sidePanel.add(teamPanel, BorderLayout.CENTER);
    }

    public void setupCenterPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(primaryColor);
        JPanel selectPanel = new JPanel();
        selectPanel.setBackground(primaryColor);
        JPanel confirmPanel = new JPanel();
        confirmPanel.setBackground(primaryColor);

        centerPanel.setLayout(new BorderLayout());

        // Set the preferred size of the top panel to 100px
        topPanel.setPreferredSize(new Dimension(0, 100)); // Height = 100px
        confirmPanel.setPreferredSize(new Dimension(0, 100)); // Height = 100px
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(selectPanel, BorderLayout.CENTER);
        centerPanel.add(confirmPanel, BorderLayout.SOUTH);
    }

    private void setupMainFrame() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.weightx = 3.0 / 12;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(leftPanel, gbc);

        gbc.weightx = 6.0 / 12;
        gbc.gridx = 1;
        frame.add(centerPanel, gbc);

        gbc.weightx = 3.0 / 12;
        gbc.gridx = 2;
        frame.add(rightPanel, gbc);
    }

    private String selectedChampion = null;

    public String getChampionChoice() {
        selectedChampion = null;

        while (selectedChampion == null) {
            try {
                Thread.sleep(100); // Wait until a button is pressed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return selectedChampion;
    }

    /**
     * Adds red borders to all visible JPanels in the GUI for testing purposes.
     */
    private void testing_borders() {
        addBorders(frame.getContentPane());
    }

    /**
     * Recursively adds red borders to all visible JPanels in the given container.
     *
     * @param container The container to search for JPanels.
     */
    private void addBorders(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JPanel && component.isVisible()) {
                ((JPanel) component).setBorder(BorderFactory.createLineBorder(Color.RED, 5));
            }
            if (component instanceof Container) {
                addBorders((Container) component); // Recursively check child containers
            }
        }
    }
}
import javax.swing.*;
import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

class GameUI {
    private String role;
    private ArrayList<String> team;
    private OutputStream outputStream;
    private String turn = "Server"; // or "Client" depending on who starts

    public GameUI(String role, ArrayList<String> team, OutputStream outputStream) {
        this.role = role;
        this.team = team;
        this.outputStream = outputStream;
    }

    public void createUI() {
        JFrame frame = new JFrame(role + " Champion Select");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JLabel instructions = new JLabel("Select 4 champions:");
        instructions.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(instructions, BorderLayout.NORTH);

        JPanel championsPanel = new JPanel();
        championsPanel.setLayout(new GridLayout(2, 5));
        String[] champions = {"Warrior", "Mage", "Archer", "Rogue", "Healer", "Tank", "Assassin", "Knight", "Hunter", "Druid"};

        for (String champion : champions) {
            JButton button = new JButton(champion);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (team.size() < 4) {
                        team.add(champion);
                        button.setEnabled(false);
                        if (team.size() == 4) {
                            instructions.setText("Team complete! Waiting for opponent...");
                            sendTeam();
                        }
                    }
                }
            });
            championsPanel.add(button);
        }

        frame.add(championsPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void sendTeam() {
        try (PrintWriter writer = new PrintWriter(outputStream, true)) {
            writer.println(String.join(",", team));
        }
    }
}
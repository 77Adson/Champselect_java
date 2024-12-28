// drawUI.java
import javax.swing.*;
import java.awt.*;

public class drawUI {
    public static void drawChampionSelectionUI(JFrame frame, String[] champions) {
        frame.setLayout(new GridLayout(0, 2));
        for (String champion : champions) {
            JButton button = new JButton(champion);
            frame.add(button);
        }
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
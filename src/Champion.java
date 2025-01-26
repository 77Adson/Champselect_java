import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
// import java.net.URL;

public class Champion {
    private String name;
    private ImageIcon image;

    // Konstruktor, który ładuje obraz z folderu 'images'
    public Champion(String name, String imagePath) {
        this.name = name;

        //Check if image exists, if not use placeholder
        try {
            BufferedImage imageBuffered = ImageIO.read(new File("C:\\Users\\user\\Documents\\GitHub\\Java_champselect\\images\\placeholder.jpg"));
            image = new ImageIcon(imageBuffered);
        } catch (IOException e) {
            // Handle the error here
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public ImageIcon getImage() {
        return image;
    }
}

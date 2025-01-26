import javax.imageio.IIOException;
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

    try {
        File load = new File("images\\" + imagePath);
        if (load.exists()) {
            BufferedImage imageBuffered = ImageIO.read(load);
            image = new ImageIcon(imageBuffered);
        } else {
            // Handle the case where the file is not found
            image = new ImageIcon("images\\placeholder.jpg"); // or some other default image
        }
    } catch (IIOException e) {
        // Handle the case where the file cannot be read
        image = new ImageIcon("images\\placeholder.jpg"); // or some other error image
    } catch (IOException e) {
            // TODO Auto-generated catch block
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

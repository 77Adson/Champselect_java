import javax.swing.ImageIcon;
import java.net.URL;

public class Champion {
    private String name;
    private ImageIcon image;

    // Konstruktor, który ładuje obraz z folderu 'images'
    public Champion(String name, String imagePath) {
        this.name = name;

        // Ładowanie obrazu z folderu 'images'
        URL imgURL = getClass().getClassLoader().getResource("images/" + imagePath);

        if (imgURL != null) {
            this.image = new ImageIcon(imgURL);
        } else {
            System.out.println("Nie udało się załadować obrazu: " + imagePath);
        }
    }

    public String getName() {
        return name;
    }

    public ImageIcon getImage() {
        return image;
    }
}

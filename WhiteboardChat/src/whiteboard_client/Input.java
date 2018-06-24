package whiteboard_client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public enum Input {
    TEXT(""),
    SQUARE("src/resources/blokje.stp"),
    CIRCLE("src/resources/cirkel.stp"),
    SPHERE("src/resources/rondje.stp"),
    SMILEY("src/resources/smiley.stp"),
    SOLID("src/resources/solid.stp");

    private final String imagePath;
    Input(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean[][] getStamp() {
        boolean[][] stamp = new boolean[0][0];
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(imagePath));
            stamp = (boolean[][])ois.readObject();
            ois.close();
        } catch(ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return stamp;
    }
}

package whiteboard_client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public enum Input {
    TEXT(""),
    RING(""),
    USER(""),
    SQUARE("/resources/blokje.stp"),
    CIRCLE("/resources/cirkel.stp"),
    SPHERE("/resources/rondje.stp"),
    SMILEY("/resources/smiley.stp"),
    SOLID("/resources/solid.stp");

    private final String imagePath;

    Input(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean[][] getStamp() {
        boolean[][] stamp = new boolean[0][0];
        try {
            ObjectInputStream ois = new ObjectInputStream(getClass().getResourceAsStream(imagePath));
            stamp = (boolean[][]) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return stamp;
    }
}

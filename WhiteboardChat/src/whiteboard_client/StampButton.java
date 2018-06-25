package whiteboard_client;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class StampButton extends JButton {
    private boolean[][] stamp;
    private Input input;

    StampButton(String name, String imagePath, Input input) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(imagePath));
            stamp = (boolean[][]) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        this.input = input;
        setText(name);
    }

    public boolean[][] getStamp() {
        return stamp;
    }

    public Input getInput() {
        return input;
    }
}

package whiteboard_client;

import javax.swing.*;

class InputButton extends JButton {
    private Input input;

    InputButton(String name, Input input) {
        this.input = input;
        setText(name);
    }

    Input getInput() {
        return input;
    }
}

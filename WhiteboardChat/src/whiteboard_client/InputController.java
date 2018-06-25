package whiteboard_client;

import shared.messages.client.UserMessage;
import shared.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class InputController implements ActionListener, FocusListener {
    private WhiteboardClient client;
    private WhiteboardView view;

    InputController(WhiteboardClient client, WhiteboardView view) {
        this.client = client;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(e.getSource() instanceof InputButton)) {
            client.setInputOption(Input.TEXT);
            return;
        }

        InputButton btn = (InputButton) e.getSource();
        Input inputOption = btn.getInput();

        if(inputOption.equals((Input.USER))) {
            Color newColor = view.changeColorDialog();
            if(newColor == null) {
                return;
            }
            User u = new User(client.getUser().getName(), newColor);
            client.setUser(u);
            client.sendMessage(new UserMessage(u));
            return;
        }

        if (inputOption.equals(Input.RING)) {
            client.setRing(view.ringDialog());
        }
        client.setInputOption(inputOption);
    }

    @Override
    public void focusGained(FocusEvent e) {
        client.setInputOption(Input.TEXT);
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}

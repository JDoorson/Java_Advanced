package whiteboard_client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class InputController implements ActionListener, FocusListener {
    private WhiteboardClient client;

    InputController(WhiteboardClient client) {
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!(e.getSource() instanceof StampButton)) {
            client.setInputOption(Input.TEXT);
            return;
        }

        StampButton btn = (StampButton)e.getSource();
        client.setInputOption(btn.getInput());
    }

    @Override
    public void focusGained(FocusEvent e) {
        client.setInputOption(Input.TEXT);
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}

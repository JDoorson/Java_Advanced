package whiteboard_client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowController extends WindowAdapter {
    private WhiteboardClient client;

    WindowController(WhiteboardClient client) {
        this.client = client;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        client.disconnect();
        System.exit(0);
    }
}

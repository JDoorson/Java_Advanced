package whiteboard_client;

import shared.model.User;

import java.util.Timer;
import java.util.TimerTask;

class TestClient extends WhiteboardClient {
    TestClient(ServerConfiguration config, User user) {
        super(config, user);
        TimerTask task = new TestSender(this);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(task, 1000, 5000);
    }
}

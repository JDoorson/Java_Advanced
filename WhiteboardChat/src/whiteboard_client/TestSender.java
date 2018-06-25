package whiteboard_client;

import shared.messages.client.DrawingMessage;
import shared.model.drawing.TextDrawing;

import java.awt.*;
import java.util.Random;
import java.util.TimerTask;

public class TestSender extends TimerTask {
    private TestClient client;

    TestSender(TestClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        Random rng = new Random();
        Point p = new Point(rng.nextInt(300), rng.nextInt(300));
        client.sendMessage(new DrawingMessage(client.getUser(), new TextDrawing(p, String.format("Hi, I'm %s!", client.getUser().getName()))));
    }
}

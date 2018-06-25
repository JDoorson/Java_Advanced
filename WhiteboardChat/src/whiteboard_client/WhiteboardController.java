package whiteboard_client;

import shared.messages.client.DrawingMessage;
import shared.model.drawing.Line;
import shared.model.drawing.Stamp;
import shared.model.drawing.TextDrawing;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WhiteboardController implements MouseListener {
    private WhiteboardClient client;
    private WhiteboardView view;
    private Point lineStart;

    WhiteboardController(WhiteboardClient client, WhiteboardView view) {
        this.client = client;
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!(e.getButton() == MouseEvent.BUTTON1)) {
            return;
        }

        Point p = new Point(e.getX(), e.getY());

        switch (client.getInputOption()) {
            case TEXT:
                client.sendMessage(new DrawingMessage(client.getUser(), new TextDrawing(p, view.getUserInput())));
                break;
            case SQUARE:
            case CIRCLE:
            case SPHERE:
            case SMILEY:
            case SOLID:
                client.sendMessage(new DrawingMessage(client.getUser(), new Stamp(p, client.getInputOption().getStamp())));
                break;
            default:
                break;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            lineStart = new Point(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Point lineEnd = new Point(e.getX(), e.getY());
            if (!lineStart.equals(lineEnd)) {
                client.sendMessage(new DrawingMessage(client.getUser(), new Line(lineStart, lineEnd)));
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}

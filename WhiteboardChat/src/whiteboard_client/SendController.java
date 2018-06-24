package whiteboard_client;

import shared.messages.client.DrawingMessage;
import shared.model.drawing.TextDrawing;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SendController implements MouseListener{
    private WhiteboardClient client;
    private WhiteboardView view;

    public SendController(WhiteboardClient client, WhiteboardView view) {
        this.client = client;
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            Point p = new Point(e.getX(), e.getY());
            client.sendMessage(new DrawingMessage(client.getUser(), new TextDrawing(p, view.getUserInput())));
            //view.clearUserInput();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
